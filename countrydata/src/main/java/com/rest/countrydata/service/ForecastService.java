package com.rest.countrydata.service;

import com.rest.countrydata.persistence.models.forecast.ForecastEntity;

import java.util.List;


public interface ForecastService {
	public List<ForecastEntity> getForecast(List<String> cityList, boolean isPersist);

	public void save(ForecastEntity forecastEntity);

    public void saveAll(List<ForecastEntity> forecastEntityList);
}
