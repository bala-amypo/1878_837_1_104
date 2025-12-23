// com/example/demo/model/DeviceCatalogItem.java
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class DeviceCatalogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String deviceCode;

    @Column(nullable = false)
    private String deviceType;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer maxAllowedPerEmployee;

    private Boolean active = true;

    public DeviceCatalogItem(String deviceCode, String deviceType, String model, Integer maxAllowedPerEmployee) {
        this.deviceCode = deviceCode;
        this.deviceType = deviceType;
        this.model = model;
        this.maxAllowedPerEmployee = maxAllowedPerEmployee;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getMaxAllowedPerEmployee() {
        return maxAllowedPerEmployee;
    }

    public void setMaxAllowedPerEmployee(Integer maxAllowedPerEmployee) {
        this.maxAllowedPerEmployee = maxAllowedPerEmployee;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}