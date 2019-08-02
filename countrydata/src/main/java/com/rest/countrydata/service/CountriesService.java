package com.rest.countrydata.service;


import com.rest.countrydata.persistence.models.country.CountryEntity;

import java.util.List;

public interface CountriesService {
    public List getCountries(List<String> countryCodeList, boolean isPersist);

    public CountryEntity findCountryByalpha3Code(String alpha3Code);

    public void save(CountryEntity countryEntity);

    public void saveList(List<CountryEntity> countryEntityList);

}
