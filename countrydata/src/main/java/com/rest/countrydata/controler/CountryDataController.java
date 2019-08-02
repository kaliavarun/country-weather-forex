package com.rest.countrydata.controler;

import com.rest.countrydata.persistence.models.country.CountryEntity;
import com.rest.countrydata.persistence.models.forecast.ForecastEntity;
import com.rest.countrydata.persistence.models.forex.ForeignExchangeEntity;
import com.rest.countrydata.service.CountriesService;
import com.rest.countrydata.service.ForecastService;
import com.rest.countrydata.service.ForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
@RestController
public class CountryDataController {

    @Autowired
    private ForecastService forecastService;

    @Autowired
    private ForexService forexService;

    @Autowired
    private CountriesService countriesService;

    @RequestMapping("/forecast")
    public void getForecast() {
        List<ForecastEntity> forecastEntityList = forecastService.getForecast(Arrays.asList("Mumbai"), true);
    }

    @RequestMapping("/forex")
    public void getForex() {
        ForeignExchangeEntity foreignExchangeEntity = forexService.getExchangeRate(Arrays.asList("GBP,JPY,EUR"), true);
        System.out.println(foreignExchangeEntity.toString());
    }

    @RequestMapping("/countries")
    public void getCountries() {
        // Country Data
        List<CountryEntity> countryList = countriesService.getCountries(Arrays.asList("AUS;BRA;CHN;GBR;USA"), true);
    }

    @RequestMapping(value = "/reports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ReportDTO> getCountryReports() {
        // Countries can be retrieved from user input from html page
        List<String> countrysList = Arrays.asList("AUS", "BRA", "CHN", "GBR", "USA");
        List<CountryEntity> countryList = countriesService.getCountries(countrysList, true);

        List<String> capitalCities = new ArrayList<>();
        Map<String, String> currencyMap = new HashMap<>();
        countryList.forEach((country) -> {
            capitalCities.add(country.getCapital());
            currencyMap.put(country.getCapital(), country.getCurrencies().get(0).getCode());
        });

        List<ForecastEntity> forecastEntityList = forecastService.getForecast(capitalCities, true);
        Map<String, Double> avgTemp = new HashMap<>();
        Map<String, Double> avgTempMin = new HashMap<>();
        Map<String, Double> avgTempMax = new HashMap<>();
        Map<String, Double> avgWindSpeed = new HashMap<>();
        Map<String, String> weatherMap = new HashMap<>();


        forecastEntityList.forEach(
                forecast -> {
                    avgTemp.put(forecast.getCityName(), forecast
                            .getList()
                            .stream()
                            .mapToDouble((listEntity) -> listEntity.getMain().getTemp()).summaryStatistics().getAverage());
                    avgTempMin.put(forecast.getCityName(), forecast
                            .getList()
                            .stream()
                            .mapToDouble((listEntity) -> listEntity.getMain().getTempMin()).summaryStatistics().getAverage());
                    avgTempMax.put(forecast.getCityName(), forecast
                            .getList()
                            .stream()
                            .mapToDouble((listEntity) -> listEntity.getMain().getTempMax()).summaryStatistics().getAverage());
                    avgWindSpeed.put(forecast.getCityName(), forecast
                            .getList()
                            .stream()
                            .mapToDouble((listEntity) -> listEntity.getWindEntity().getSpeed()).summaryStatistics().getAverage());
                    weatherMap.put(forecast.getCityName(), forecast
                            .getList().get(0).getWeatherEntity().getDescription());// Adding only first instance for simplicity
                }
        );



        ForeignExchangeEntity foreignExchangeEntity = forexService.getExchangeRate(new ArrayList<String>(currencyMap.values()), true);

        // DT Object construction
        List<ReportDTO> reportDTOList = new ArrayList<>();
        for (CountryEntity countryEntity : countryList){
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setCountry(countryEntity.getName());
            reportDTO.setCallingCode(countryEntity.getCallingCodes().get(0).getCallingCode());// There is only one calling code for simplicity
            reportDTO.setCapitalCity(countryEntity.getCapital());
            reportDTO.setCurrency(countryEntity.getCurrencies().get(0).getCurrencyName());// There is only one currency for simplicity
            reportDTO.setFlag(countryEntity.getFlag());
            reportDTO.setPopulation(countryEntity.getPopulation().toString());
            reportDTO.setTemperature(avgTemp.get(countryEntity.getCapital()).toString());
            reportDTO.setTemperatureMinimum(avgTempMin.get(countryEntity.getCapital()).toString());
            reportDTO.setTempperatureMaximum(avgTempMax.get(countryEntity.getCapital()).toString());
            reportDTO.setExchangeRate(foreignExchangeEntity.getRatesList().stream().
                    filter( curr -> curr.getCurrency().equals(currencyMap.get(countryEntity.getCapital()))).findAny().orElse(null).getRate().toString());
            reportDTO.setWeatherDescreption(weatherMap.get(countryEntity.getCapital()));
            reportDTO.setTemperatureAverageCapital(avgTemp.get(countryEntity.getCapital()).toString());
            reportDTO.setWinSpeedAverageCapital(avgWindSpeed.get(countryEntity.getCapital()).toString());

            reportDTOList.add(reportDTO);
        }

        //reportDTO.setCountry();


        return reportDTOList;

    }
}
