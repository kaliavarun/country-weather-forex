package com.rest.countrydata.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.countrydata.handler.OWMResponseErrorHandler;
import com.rest.countrydata.model.forex.ForeignExchange;
import com.rest.countrydata.persistence.models.forex.ForeignExchangeEntity;
import com.rest.countrydata.persistence.models.forex.RatesEntity;
import com.rest.countrydata.persistence.repository.ForexRepository;
import com.rest.countrydata.service.ForexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ForexServiceImpl implements ForexService {

    private static final Logger logger = LoggerFactory.getLogger(ForexServiceImpl.class);

    private final RestTemplate restTemplate;

    @Autowired
    private ForexRepository forexRepository;

    @Value("${app.forex.api.key}")
    private String apiKey;

    @Value("${app.forex.api.url}")
    private String apiUrl;

    public ForexServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.setErrorHandler(new OWMResponseErrorHandler());
    }

    @Override
    public ForeignExchangeEntity getExchangeRate(List<String> currencies, boolean isPersist) {
        ForeignExchangeEntity forecastEntity = new ForeignExchangeEntity();
        if (currencies != null) {
            String currencyList = String.join(",", currencies);
            logger.debug("Requesting current weather for {}", currencyList);
            logger.debug("API key {} , url {}", apiKey, apiUrl);
            String forex = null;
            if (validParameters(currencyList)) {
                URI url = new UriTemplate(this.apiUrl).expand(this.apiKey, currencyList);

                forex = invoke(url, String.class);
                ForeignExchange foreignExchange = jsonToForexTypeRef(forex);
                forecastEntity = convertToEntity(foreignExchange);
                if(isPersist){
                    forexRepository.save(forecastEntity);
                }
            }
        }
        return forecastEntity;
    }

    private boolean validParameters(String city) {
        return city != null && !"".equals(city) && apiKey != null && !"".equals(apiKey) && apiUrl != null && !"".equals(apiUrl);
    }

    @Transactional
    private ForeignExchangeEntity convertToEntity(ForeignExchange foreignExchange) {
        ForeignExchangeEntity foreignExchangeEntity = new ForeignExchangeEntity();
        if (foreignExchange != null) {
            // Order of objects is very important . before passing refference to
            // child object, parent must be populated
            foreignExchangeEntity = new ForeignExchangeEntity();
            foreignExchangeEntity.setBase(foreignExchange.getBase());
            foreignExchangeEntity.setDate(foreignExchange.getDate());
            foreignExchangeEntity.setSuccess(foreignExchange.getSuccess());
            foreignExchangeEntity.setTimestamp(foreignExchange.getTimestamp());

            List<RatesEntity> rateList = new ArrayList<>();

            for (Map.Entry<String, String> entry : foreignExchange.getRates().entrySet()) {
                RatesEntity ratesEntity = new RatesEntity();
                ratesEntity.setCurrency(entry.getKey());
                ratesEntity.setRate(Double.valueOf(entry.getValue()));
                ratesEntity.setForeignExchangeEntity(foreignExchangeEntity);

                rateList.add(ratesEntity);
            }
            foreignExchangeEntity.setRatesList(rateList);
        }
        return foreignExchangeEntity;
    }

    private ForeignExchange jsonToForexTypeRef(String json) {
        ForeignExchange o = new ForeignExchange();
        if (json != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                o = mapper.readValue(json, ForeignExchange.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
