package com.rest.countrydata.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@Component
@EnableTransactionManagement
@ComponentScan({"com.rest.countrydata.*"})
@EnableJpaRepositories(basePackages = "com.rest.countrydata.persistence")
@EntityScan("com.rest.countrydata.persistence.models.*")
public class PersistenceConfig {
}
