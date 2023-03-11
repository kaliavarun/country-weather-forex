package com.rest.countrydata.persistence.repository;

import com.rest.countrydata.persistence.models.country.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, String> {
    public CountryEntity findByAlpha3Code(String alpha3Code);
}
