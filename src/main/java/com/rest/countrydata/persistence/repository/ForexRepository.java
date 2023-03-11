package com.rest.countrydata.persistence.repository;

import com.rest.countrydata.persistence.models.forex.ForeignExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexRepository extends JpaRepository<ForeignExchangeEntity, String> {
}
