// com/example/demo/model/IssuedDeviceRecord.java
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
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

    private String status = "ISSUED";

    public IssuedDeviceRecord(EmployeeProfile employee, DeviceCatalogItem deviceItem, LocalDate issuedDate) {
        this.employee = employee;
        this.deviceItem = deviceItem;
        this.issuedDate = issuedDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeProfile getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeProfile employee) {
        this.employee = employee;
    }

    public DeviceCatalogItem getDeviceItem() {
        return deviceItem;
    }

    public void setDeviceItem(DeviceCatalogItem deviceItem) {
        this.deviceItem = deviceItem;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}