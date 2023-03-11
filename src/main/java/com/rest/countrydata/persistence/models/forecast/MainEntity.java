
package com.rest.countrydata.persistence.models.forecast;

import jakarta.persistence.*;

@Entity
public class MainEntity {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "listid", insertable = true)
    private ListEntity listEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mainId", nullable = false, unique = true)
    private Integer mainId;

    @Column(name = "temp", nullable = true, unique = false)
    private Double temp;

    @Column(name = "tempMin", nullable = true, unique = false)
    private Double tempMin;

    @Column(name = "tempMax", nullable = true, unique = false)
    private Double tempMax;

    @Column(name = "pressure", nullable = true, unique = false)
    private Double pressure;

    @Column(name = "seaLevel", nullable = true, unique = false)
    private Double seaLevel;

    @Column(name = "grndLevel", nullable = true, unique = false)
    private Double grndLevel;

    @Column(name = "humidity", nullable = true, unique = false)
    private Integer humidity;

    @Column(name = "tempKf", nullable = true, unique = false)
    private Integer tempKf;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getTempKf() {
        return tempKf;
    }

    public void setTempKf(Integer tempKf) {
        this.tempKf = tempKf;
    }

    public ListEntity getListEntity() {
        return listEntity;
    }

    public void setListEntity(ListEntity listEntity) {
        this.listEntity = listEntity;
    }

    public Integer getMainId() {
        return mainId;
    }

    public void setMainId(Integer mainId) {
        this.mainId = mainId;
    }
}
