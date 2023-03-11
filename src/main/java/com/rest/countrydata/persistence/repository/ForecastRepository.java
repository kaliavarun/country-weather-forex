package com.rest.countrydata.persistence.repository;

import com.rest.countrydata.persistence.models.forecast.ForecastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastRepository extends JpaRepository<ForecastEntity, String> {

}
