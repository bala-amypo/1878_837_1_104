package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "issued_devices")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class IssuedDeviceRecord {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;

    @ManyToOne
    @JoinColumn(name = "device_item_id", nullable = false)
    private DeviceCatalogItem deviceItem;

    @Column(nullable = false)
    private LocalDate issuedDate;

    private LocalDate returnedDate;

    @Column(nullable = false)
    private String status = "ISSUED"; 
}