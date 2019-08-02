package com.rest.countrydata.service;

import com.rest.countrydata.persistence.models.forex.ForeignExchangeEntity;

import java.util.List;


public interface ForexService {
	public ForeignExchangeEntity getExchangeRate(List<String> currencies, boolean isPersist);
}
