package com.rest.countrydata.persistence.models.country;


import jakarta.persistence.*;

@Entity
public class CallingCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codeId", nullable = false, unique = true)
    private Integer codeId;

    @Column(name = "codeName", nullable = false, unique = true)
    private String callingCode;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryEntity countryEntity;

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getCode() {
        return callingCode;
    }

    public void setCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }
}
