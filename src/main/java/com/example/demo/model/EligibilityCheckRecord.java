// File: src/main/java/com/example/demo/model/EligibilityCheckRecord.java
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

    public EligibilityCheckRecord() {}

    // Getters
    public Long getId() { return id; }
    public EmployeeProfile getEmployee() { return employee; }
    public DeviceCatalogItem getDeviceItem() { return deviceItem; }
    public boolean isEligible() { return isEligible; }
    public String getReason() { return reason; }
    public LocalDateTime getCheckedAt() { return checkedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setEmployee(EmployeeProfile employee) { this.employee = employee; }
    public void setDeviceItem(DeviceCatalogItem deviceItem) { this.deviceItem = deviceItem; }
    public void setEligible(boolean eligible) { this.isEligible = eligible; }
    public void setReason(String reason) { this.reason = reason; }
}