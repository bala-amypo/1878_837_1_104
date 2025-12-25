package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class DeviceCatalogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String deviceCode;

    @Column(nullable = false)
    private String deviceType; // LAPTOP / MONITOR / etc.

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int maxAllowedPerEmployee;

    @Column(nullable = false)
    private boolean active = true;

    // No-args constructor
    public DeviceCatalogItem() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDeviceCode() { return deviceCode; }
    public void setDeviceCode(String deviceCode) { this.deviceCode = deviceCode; }

    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getMaxAllowedPerEmployee() { return maxAllowedPerEmployee; }
    public void setMaxAllowedPerEmployee(int maxAllowedPerEmployee) { this.maxAllowedPerEmployee = maxAllowedPerEmployee; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}