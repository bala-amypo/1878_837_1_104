package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "device_catalog",
       uniqueConstraints = @UniqueConstraint(columnNames = "deviceCode"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DeviceCatalogItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String deviceCode;

    @Column(nullable = false)
    private String deviceType; // LAPTOP, MONITOR, etc.

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer maxAllowedPerEmployee;

    private boolean active = true;
}