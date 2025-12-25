package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EligibilityCheckRecord {

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
    private boolean isEligible;

    @Column(nullable = false, length = 1000)
    private String reason;

    @Column(nullable = false, updatable = false)
    private LocalDateTime checkedAt;

    @PrePersist
    protected void onCreate() {
        checkedAt = LocalDateTime.now();
    }

    // No-args constructor
    public EligibilityCheckRecord() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EmployeeProfile getEmployee() { return employee; }
    public void setEmployee(EmployeeProfile employee) { this.employee = employee; }

    public DeviceCatalogItem getDeviceItem() { return deviceItem; }
    public void setDeviceItem(DeviceCatalogItem deviceItem) { this.deviceItem = deviceItem; }

    public boolean isEligible() { return isEligible; }
    public void setEligible(boolean eligible) { isEligible = eligible; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDateTime getCheckedAt() { return checkedAt; }
}