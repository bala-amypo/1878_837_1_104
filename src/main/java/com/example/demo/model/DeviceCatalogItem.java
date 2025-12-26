package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class DeviceCatalogItem {

    @Id
    @GeneratedValue
    private Long id;

    private String deviceName;
    private boolean available = true;

    public Long getId() { return id; }
    public String getDeviceName() { return deviceName; }
    public boolean isAvailable() { return available; }

    public void setId(Long id) { this.id = id; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public void setAvailable(boolean available) { this.available = available; }
}
