
package com.rest.countrydata.persistence.models.country;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;


@Entity
public class CountryEntity {

    public CountryEntity() {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "country_id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "alpha3Code", nullable = false, unique = true)
    private String alpha3Code;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "countryEntity")
    @JsonManagedReference
    private List<CallingCodeEntity> callingCodes = null;

    @Column(name = "capital")
    private String capital;

    @Column(name = "population")
    private Integer population;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "countryEntity")
    @JsonManagedReference
    private List<CurrencyEntity> currencies = null;

    @Column(name = "flag")
    private String flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CallingCodeEntity> getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(List<CallingCodeEntity> callingCodes) {
        this.callingCodes = callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public List<CurrencyEntity> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<CurrencyEntity> currencies) {
        this.currencies = currencies;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }
}
