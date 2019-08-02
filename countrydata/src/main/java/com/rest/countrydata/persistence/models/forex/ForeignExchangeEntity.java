
package com.rest.countrydata.persistence.models.forex;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class ForeignExchangeEntity {

    @Id
    @Column(name = "forexId", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer forexId;

    @Column(name = "success", nullable = true, unique = false)
    private Boolean success;

    @Column(name = "timestamp", nullable = true, unique = false)
    private Long timestamp;

    @Column(name = "base", nullable = true, unique = false)
    private String base;

    @Column(name = "date", nullable = true, unique = false)
    private String date;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "foreignExchangeEntity")
    @JsonManagedReference
    private List<RatesEntity> ratesList;

    public List<RatesEntity> getRatesList() {
        return ratesList;
    }

    public void setRatesList(List<RatesEntity> ratesList) {
        this.ratesList = ratesList;
    }

    public ForeignExchangeEntity() {

    }

    public Integer getForexId() {
        return forexId;
    }

    public void setForexId(Integer forexId) {
        this.forexId = forexId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
