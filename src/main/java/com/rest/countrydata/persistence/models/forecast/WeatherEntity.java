
package com.rest.countrydata.persistence.models.forecast;

import jakarta.persistence.*;

@Entity
public class WeatherEntity {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "listid")
    private ListEntity listEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "main", nullable = true, unique = false)
    private String main;

    @Column(name = "description", nullable = true, unique = false)
    private String description;

    @Column(name = "icon", nullable = true, unique = false)
    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public ListEntity getListEntity() {
        return listEntity;
    }

    public void setListEntity(ListEntity listEntity) {
        this.listEntity = listEntity;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
