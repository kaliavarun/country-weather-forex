package com.rest.countrydata.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.countrydata.handler.OWMResponseErrorHandler;
import com.rest.countrydata.model.forecast.Forecast;
import com.rest.countrydata.persistence.models.forecast.CityEntity;
import com.rest.countrydata.persistence.repository.CitiesRepository;
import com.rest.countrydata.persistence.repository.ForecastRepository;
import com.rest.countrydata.service.ForecastService;
import com.rest.countrydata.persistence.models.forecast.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ForecastServiceImpl implements ForecastService {

    private static final Logger logger = LoggerFactory.getLogger(ForecastServiceImpl.class);

    private final RestTemplate restTemplate;

    @Autowired
    private ForecastRepository forecastRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Value("${app.forcast.api.key}")
    private String apiKey;

    @Value("${app.forcast.api.url}")
    private String apiUrl;

    public ForecastServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.setErrorHandler(new OWMResponseErrorHandler());
    }

    @Cacheable("forecast")
    @Override
    public List<ForecastEntity> getForecast(List<String> cityList, boolean isPersist) {
        List<ForecastEntity> forecastEntityList = new ArrayList<>();
        if (cityList != null) {
            for (String city : cityList) {
                logger.debug("Requesting current weather for {}", city);
                logger.debug("API key {} , url {}", apiKey, apiUrl);
                String var = null;
                if (validParameters(city)) {
                    URI url = new UriTemplate(this.apiUrl).expand(city, this.apiKey);

                    var = invoke(url, String.class);
                    Forecast forecast = jsonToCountryTypeRef(var);
                    ForecastEntity forecastEntity = convertToEntity(forecast, city);
                    forecastEntityList.add(forecastEntity);
                }

            }
            if(isPersist){
                saveAll(forecastEntityList);
            }
        }

        return forecastEntityList;
    }

    @Override
    public void save(ForecastEntity forecastEntity) {
        forecastRepository.save(forecastEntity);
    }

    @Override
    @Transactional
    public void saveAll(List<ForecastEntity> forecastEntityList) {
        if(forecastEntityList != null){
            forecastEntityList.forEach(forecastEntity -> save(forecastEntity));
        }
    }

    @Transactional
    private ForecastEntity convertToEntity(Forecast forecast, String city) {

        ForecastEntity forecastEntity = new ForecastEntity();
        CityEntity cityEntity = citiesRepository.findByCityName(city);
        if(cityEntity == null){
            cityEntity = new CityEntity();
            cityEntity.setCityName(city);
            cityEntity = citiesRepository.save(cityEntity);
        }
        forecastEntity.setCity(cityEntity);
        forecastEntity.setCnt(forecast.getCnt());
        forecastEntity.setCod(forecast.getCod());
        forecastEntity.setMessage(forecast.getMessage());

        List<ListEntity> listOfListEntities = new ArrayList<>();

        for (com.rest.countrydata.model.forecast.List listData : forecast.getList()) {
            ListEntity listEntity = new ListEntity();
            MainEntity mainEntity = new MainEntity();
            // set main
            mainEntity.setGrndLevel(listData.getMain().getGrndLevel());
            mainEntity.setHumidity(listData.getMain().getHumidity());
            mainEntity.setPressure(listData.getMain().getPressure());
            mainEntity.setSeaLevel(listData.getMain().getSeaLevel());
            mainEntity.setTemp(listData.getMain().getTemp());
            mainEntity.setTempKf(listData.getMain().getTempKf());
            mainEntity.setTempMax(listData.getMain().getTempMax());
            mainEntity.setTempMin(listData.getMain().getTempMin());
            mainEntity.setListEntity(listEntity);

            // The getWeather() will awlays return a list with single element will always have one
            WeatherEntity weatherEntity = new WeatherEntity();
            weatherEntity.setDescription(listData.getWeather().get(0).getDescription());
            weatherEntity.setMain(listData.getWeather().get(0).getMain());
            weatherEntity.setListEntity(listEntity);


            WindEntity windEntity = new WindEntity();
            windEntity.setDeg(listData.getWind().getDeg());
            windEntity.setSpeed(listData.getWind().getSpeed());
            windEntity.setListEntity(listEntity);

            listEntity.setDt(listData.getDt());
            listEntity.setMain(mainEntity);
            listEntity.setWeatherEntity(weatherEntity);
            listEntity.setWindEntity(windEntity);
            listEntity.setForecastEntity(forecastEntity);
            listOfListEntities.add(listEntity);
        }
        forecastEntity.setList(listOfListEntities);

        return forecastEntity;
    }

    private boolean validParameters(String city) {
        return city != null && !"".equals(city) && apiKey != null && !"".equals(apiKey) && apiUrl != null && !"".equals(apiUrl);
    }

    private Forecast jsonToCountryTypeRef(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Forecast forecast = null;
        Forecast o = null;
        try {
            o = mapper.readValue(json, Forecast.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }

    private <T> T invoke(URI url, Class<T> responseType) {
        T forecast = null;
        try {
            RequestEntity<?> request = RequestEntity.get(url)
                    .accept(MediaType.APPLICATION_JSON).build();
            ResponseEntity<T> exchange = this.restTemplate
                    .exchange(request, responseType);
            forecast = exchange.getBody();
        } catch (Exception e) {
            logger.error("An error occurred while calling openweathermap.org API endpoint:  " + e.getMessage());
        }

        return forecast;
    }
}
