
package com.rest.countrydata.persistence.models.forecast;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import java.util.List;

/**
 * Each forecast instance represents a single resultset
 */
@Entity
public class ForecastEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forecastId", nullable = false, unique = true)
    private Integer forecastId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @JsonManagedReference
    private CityEntity city;

    @Column(name = "cod", nullable = true, unique = false)
    private String cod;

    @Column(name = "message", nullable = true, unique = false)
    private Double message;

    @Column(name = "cnt", nullable = true, unique = false)
    private Integer cnt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "forecastEntity")
    private List<ListEntity> list = null;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public Integer getForecastId() {
        return forecastId;
    }

    public void setForecastId(Integer forecastId) {
        this.forecastId = forecastId;
    }

    public String getCityName(){
        return city.getCityName();
    }

    @Override
    public String toString() {
        return "ForecastEntity{" +
                "cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + list +
                ", city=" + city + '}';
    }
}
