
package com.rest.countrydata.persistence.models.forecast;


import javax.persistence.*;

@Entity
/**
 * CityEntity for which data is to be retrieved
 */
public class CityEntity {

    @Id
    @Column(name = "cityId", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cityId;

    @Column(name = "cityName", nullable = true, unique = true)
    private String cityName;

    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "city")
    private List<ForecastEntity> forecast;*/

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return cityName;
    }

    public void setcityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

 /*   public List<ForecastEntity> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastEntity> forecast) {
        this.forecast = forecast;
    }*/
}
