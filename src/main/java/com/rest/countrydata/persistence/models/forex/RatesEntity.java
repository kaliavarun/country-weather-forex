package com.rest.countrydata.persistence.models.forex;


import jakarta.persistence.*;

@Entity
public class RatesEntity {

    @Id
    @Column(name = "Id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ratesId;

    @Column(name = "success", nullable = true, unique = false)
    private String currency;

    @Column(name = "rate", nullable = true, unique = false)
    private Double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forexId")
    private ForeignExchangeEntity foreignExchangeEntity;

    public Integer getRatesId() {
        return ratesId;
    }

    public void setRatesId(Integer ratesId) {
        this.ratesId = ratesId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public ForeignExchangeEntity getForeignExchangeEntity() {
        return foreignExchangeEntity;
    }

    public void setForeignExchangeEntity(ForeignExchangeEntity foreignExchangeEntity) {
        this.foreignExchangeEntity = foreignExchangeEntity;
    }
}
