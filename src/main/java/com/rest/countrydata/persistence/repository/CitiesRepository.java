package com.rest.countrydata.persistence.repository;

import com.rest.countrydata.persistence.models.forecast.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends JpaRepository<CityEntity, String> {
    public CityEntity save(CityEntity cityEntity);

    public CityEntity findByCityName(String cityName);

}
