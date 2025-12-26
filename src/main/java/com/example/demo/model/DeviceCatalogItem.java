package com.example.demo.model;

import lombok.*;

@Getter @Setter
public class DeviceCatalogItem {
    private Long id;
    private String deviceCode;
    private String deviceType;
    private String model;
    private Integer maxAllowedPerEmployee;
    private Boolean active = true;
}
