package com.rest.countrydata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Component
@EnableTransactionManagement
@ComponentScan({"com.rest.countrydata.config"})
@EnableJpaRepositories(basePackages = "com.rest.countrydata.persistence")
public class PersistenceConfig {

    private final String DIALECT = "hibernate.dialect";
    private final String SHOW_SQL = "hibernate.show_sql";
    private final String DDL_OPS = "hibernate.hbm2ddl.auto";
    private final String FORMAT_SQL = "hibernate.format_sql";
    private final String LAZY_LOAD = "hibernate.enable_lazy_load_no_trans";
    private final String PACKAGE_TO_SCAN = "app.database.provider.packagetoscan";

    @Value("${app.database.driver}")
    private String	PROPERTY_NAME_DATABASE_DRIVER;

    @Value("${app.database.url}")
    private String	PROPERTY_NAME_DATABASE_URL;

    @Value("${app.database.username}")
    private String	PROPERTY_NAME_DATABASE_USERNAME;

    @Value("${app.database.password}")
    private String	PROPERTY_NAME_DATABASE_PASSWORD;


    @Value("${" + DIALECT + "}")
    private String	PROPERTY_NAME_HIBERNATE_DIALECT;

    @Value("${" + SHOW_SQL + "}")
    private String	PROPERTY_NAME_HIBERNATE_SHOW_SQL;

    @Value("${" + DDL_OPS + "}")
    private String	PROPERTY_NAME_HIBERNATE_HBM2DDL;

    //@Value("app.database.provider.dialect")
    //private static String	PROPERTY_NAME_HIBERNATE_IMPORT_FILES			= "hibernate.hbm2ddl.import_files";

    @Value("${" + LAZY_LOAD + "}")
    private String	PROPERTY_NAME_HIBERNATE_LAZY_INIT;

    @Value("${" + PACKAGE_TO_SCAN + "}")
    private String	PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
        dataSource.setUrl(PROPERTY_NAME_DATABASE_URL);
        dataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
        dataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(getJpaVendorAdapter());
        entityManagerFactoryBean.setPersistenceUnitName("myJpaPersistenceUnit");
        //entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);

        entityManagerFactoryBean.setJpaProperties(hibProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter() {
        JpaVendorAdapter adapter = null;
        try {
            adapter = new HibernateJpaVendorAdapter();
        }
        catch (Throwable ex){
            ex.printStackTrace();
        }
        return adapter;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(DIALECT, PROPERTY_NAME_HIBERNATE_DIALECT);
        properties.put(SHOW_SQL, PROPERTY_NAME_HIBERNATE_SHOW_SQL);
        properties.put(DDL_OPS, PROPERTY_NAME_HIBERNATE_HBM2DDL);
        properties.put(LAZY_LOAD, PROPERTY_NAME_HIBERNATE_LAZY_INIT);

        return properties;
    }
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
