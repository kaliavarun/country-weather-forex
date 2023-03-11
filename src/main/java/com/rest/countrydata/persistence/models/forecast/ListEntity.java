
package com.rest.countrydata.persistence.models.forecast;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class ListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listId", nullable = false, unique = true)
    private Integer listId;

    @Column(name = "dt", nullable = true, unique = false)
    private Integer dt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "forecastId", insertable = true)
    @JsonManagedReference
    private ForecastEntity forecastEntity;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "listEntity")
    @JsonManagedReference
    private MainEntity main;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "listEntity")
    @JsonManagedReference
    private WeatherEntity weatherEntity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "listEntity")
    @JsonManagedReference
    private WindEntity windEntity;

    public ForecastEntity getForecastEntity() {
        return forecastEntity;
    }

    public void setForecastEntity(ForecastEntity forecastEntity) {
        this.forecastEntity = forecastEntity;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public MainEntity getMain() {
        return main;
    }

    public void setMain(MainEntity main) {
        this.main = main;
    }

    public WeatherEntity getWeatherEntity() {
        return weatherEntity;
    }

    public void setWeatherEntity(WeatherEntity weatherEntity) {
        this.weatherEntity = weatherEntity;
    }

    public WindEntity getWindEntity() {
        return windEntity;
    }

    public void setWindEntity(WindEntity windEntity) {
        this.windEntity = windEntity;
    }
}
