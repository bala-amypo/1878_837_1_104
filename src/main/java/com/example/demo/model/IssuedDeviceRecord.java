// File: src/main/java/com/example/demo/model/IssuedDeviceRecord.java
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class IssuedDeviceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public IssuedDeviceRecord() {}

    // Getters
    public Long getId() { return id; }
    public EmployeeProfile getEmployee() { return employee; }
    public DeviceCatalogItem getDeviceItem() { return deviceItem; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public String getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setEmployee(EmployeeProfile employee) { this.employee = employee; }
    public void setDeviceItem(DeviceCatalogItem deviceItem) { this.deviceItem = deviceItem; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }
    public void setReturnedDate(LocalDate returnedDate) { this.returnedDate = returnedDate; }
    public void setStatus(String status) { this.status = status; }
}