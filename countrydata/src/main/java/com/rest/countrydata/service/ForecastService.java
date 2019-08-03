package com.rest.countrydata.service;

import com.rest.countrydata.persistence.models.forecast.ForecastEntity;

import java.util.List;

/**
 * Service for forecast only services. Can be used independently
 */
public interface ForecastService {
	public List<ForecastEntity> getForecast(List<String> cityList, boolean isPersist);

	public void save(ForecastEntity forecastEntity);

    public void saveAll(List<ForecastEntity> forecastEntityList);
}
