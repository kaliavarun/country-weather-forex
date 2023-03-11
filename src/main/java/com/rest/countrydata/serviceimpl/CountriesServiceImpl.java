package com.rest.countrydata.serviceimpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.countrydata.handler.OWMResponseErrorHandler;
import com.rest.countrydata.model.country.Country;
import com.rest.countrydata.model.country.Currency;
import com.rest.countrydata.persistence.models.country.CallingCodeEntity;
import com.rest.countrydata.persistence.models.country.CountryEntity;
import com.rest.countrydata.persistence.models.country.CurrencyEntity;
import com.rest.countrydata.persistence.repository.CountryRepository;
import com.rest.countrydata.service.CountriesService;
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
public class CountriesServiceImpl implements CountriesService {
    private static final Logger logger = LoggerFactory.getLogger(ForecastServiceImpl.class);

    private final RestTemplate restTemplate;

    @Autowired
    private CountryRepository countryRepository;

    /*@Value("${app.countries.api.key}")
    private String apiKey;*/

    @Value("${app.countries.api.url}")
    private String apiUrl;


    public CountriesServiceImpl(){
        this.restTemplate = new RestTemplate();
        this.restTemplate.setErrorHandler(new OWMResponseErrorHandler());
    }

    @Override
    @Cacheable("countires")
    public List<CountryEntity> getCountries(List<String> countryCodes, boolean isPersist) {
        List<CountryEntity> countryEntityList = new ArrayList<>();

        if(countryCodes == null){
            return countryEntityList;
        }
        String countryCodeList = String.join(";", countryCodes);

        logger.debug("Requesting current weather for {}", countryCodeList);
        logger.debug("url {}",apiUrl);
        String var = "";
        if(validParameters(countryCodeList)) {
            URI url = new UriTemplate(this.apiUrl).expand(countryCodeList);

            var = invoke(url, String.class);
        }
        countryEntityList = convertToEntity(jsonToCountryTypeRef(var));
        if(isPersist){
            countryEntityList.forEach((countryEntity -> countryRepository.save(countryEntity)));
        }
        return countryEntityList;
    }

    private List<CountryEntity> convertToEntity(List<Country> countryList){
        List<CountryEntity> countryEntityList = new ArrayList<>();
        for(Country country : countryList) {
            CountryEntity countryEntity = findCountryByalpha3Code(country.getAlpha3Code());
            // For simplicity only fetch and insert of new records has been implemented.
            // Update of existing records has been skipped.
            if(countryEntity == null){
                countryEntity =  new CountryEntity();
                countryEntity.setName(country.getName());
                countryEntity.setCapital(country.getCapital());
                countryEntity.setAlpha3Code(country.getAlpha3Code());

                List<CurrencyEntity> listCurrency = new ArrayList<>();
                for (Currency currency : country.getCurrencies()){
                    CurrencyEntity currencyEntity =  new CurrencyEntity();
                    currencyEntity.setName(currency.getName());
                    currencyEntity.setSymbol(currency.getSymbol());
                    currencyEntity.setCode(currency.getCode());
                    currencyEntity.setCountryEntity(countryEntity);

                    listCurrency.add(currencyEntity);
                }
                countryEntity.setCurrencies(listCurrency);
                countryEntity.setFlag(country.getFlag());
                countryEntity.setPopulation(country.getPopulation());

                List<CallingCodeEntity> listCallingCode = new ArrayList<>();
                for(String callingCode : country.getCallingCodes()){
                    CallingCodeEntity callingCodeEntity = new CallingCodeEntity();
                    callingCodeEntity.setCode(callingCode);
                    callingCodeEntity.setCountryEntity(countryEntity);
                    callingCodeEntity.setCountryEntity(countryEntity);
                    listCallingCode.add(callingCodeEntity);
                }
                countryEntity.setCallingCodes(listCallingCode);
            }
            countryEntityList.add(countryEntity);
        }
        return countryEntityList;
    }

    @Override
    public CountryEntity findCountryByalpha3Code(String alpha3Code) {
        return countryRepository.findByAlpha3Code(alpha3Code);
    }

    @Override
    public void save(CountryEntity countryEntity) {
        countryRepository.save(countryEntity);
    }

    @Override
    public void saveList(List<CountryEntity> countryEntityList) {
        countryEntityList.forEach((countryEntity -> countryRepository.save(countryEntity)));
    }

    private boolean validParameters(String city) {
        return city !=null && !"".equals(city) && apiUrl!=null && !"".equals(apiUrl);
    }

    private <T> T invoke(URI url, Class T){
        T country = null;
        try {
            RequestEntity<?> request = RequestEntity.get(url)
                    .accept(MediaType.APPLICATION_JSON).build();
            ResponseEntity<T> exchange = this.restTemplate.exchange(request, T);
            country = exchange.getBody();
        } catch(Exception e){
            logger.error("An error occurred while calling country API endpoint:  " + e.getMessage());
        }

        return country;
    }

    private List<Country> jsonToCountryTypeRef(String json){
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Country>> typeRef
                = new TypeReference<List<Country>>() {};

        List<Country> o = new ArrayList<>();
        try {
            o = mapper.readValue(json, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }

}
