
package com.rest.countrydata.persistence.models.country;

import jakarta.persistence.*;

@Entity
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "currencyId", nullable = false, unique = true)
    private Integer currencyId;

    @Column(name = "currencyCode", nullable = false, unique = true)
    private String currencyCode;

    @Column(name = "currencyName", nullable = false, unique = true)
    private String currencyName;

    @Column(name = "currencySymbol", nullable = false, unique = false)
    private String currencySymbol;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryEntity countryEntity;

    public CurrencyEntity() {
    }

    public String getCode() {
        return currencyCode;
    }

    public void setCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getName() {
        return currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }

    public void setName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getSymbol() {
        return currencySymbol;
    }

    public void setSymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

}
