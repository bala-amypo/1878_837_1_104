package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eligibility_check_records")
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
    private Boolean isEligible;

    @Column(nullable = false, length = 2000)
    private String reason;

    @Column(updatable = false)
    private LocalDateTime checkedAt;

    @PrePersist
    protected void onCreate() {
        checkedAt = LocalDateTime.now();
    }

    public EligibilityCheckRecord() {}

    public EligibilityCheckRecord(EmployeeProfile employee, DeviceCatalogItem deviceItem, Boolean isEligible, String reason) {
        this.employee = employee;
        this.deviceItem = deviceItem;
        this.isEligible = isEligible;
        this.reason = reason;
    }

    public Long getId() { return id; }
    public EmployeeProfile getEmployee() { return employee; }
    public void setEmployee(EmployeeProfile employee) { this.employee = employee; }
    public DeviceCatalogItem getDeviceItem() { return deviceItem; }
    public void setDeviceItem(DeviceCatalogItem deviceItem) { this.deviceItem = deviceItem; }
    public Boolean getIsEligible() { return isEligible; }
    public void setIsEligible(Boolean isEligible) { this.isEligible = isEligible; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LocalDateTime getCheckedAt() { return checkedAt; }
}