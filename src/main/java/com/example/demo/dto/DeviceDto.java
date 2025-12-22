package com.example.demo.dto;

public class DeviceDto {
    private String deviceCode;
    private String deviceType;
    private String model;
    private Integer maxAllowedPerEmployee;
    private Boolean active;

    // getters/setters
    public String getDeviceCode() { return deviceCode; }
    public void setDeviceCode(String v) { this.deviceCode = v; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String v) { this.deviceType = v; }
    public String getModel() { return model; }
    public void setModel(String v) { this.model = v; }
    public Integer getMaxAllowedPerEmployee() { return maxAllowedPerEmployee; }
    public void setMaxAllowedPerEmployee(Integer v) { this.maxAllowedPerEmployee = v; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean v) { this.active = v; }
}