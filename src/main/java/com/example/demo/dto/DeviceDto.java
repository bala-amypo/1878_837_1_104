package com.example.demo.dto;
public record DeviceDto(String deviceCode, String deviceType, String model,
                        Integer maxAllowedPerEmployee, Boolean active) {}