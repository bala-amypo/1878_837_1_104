package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "device_catalog_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCatalogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String deviceCode;

    @Column(nullable = false)
    private String deviceType; // LAPTOP / MONITOR / KEYBOARD / MOUSE / PHONE / TABLET

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer maxAllowedPerEmployee;

    @Column(nullable = false)
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {
        if (this.active == null) {
            this.active = true;
        }
    }

    public DeviceCatalogItem(String deviceCode, String deviceType, String model, Integer maxAllowedPerEmployee) {
        this.deviceCode = deviceCode;
        this.deviceType = deviceType;
        this.model = model;
        this.maxAllowedPerEmployee = maxAllowedPerEmployee;
        this.active = true;
    }
}
