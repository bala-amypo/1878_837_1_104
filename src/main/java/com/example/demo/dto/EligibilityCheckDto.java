package com.example.demo.dto;

import java.time.LocalDateTime;

public class EligibilityCheckDto {
    private Long employeeId;
    private Long deviceItemId;
    private Boolean isEligible;
    private String reason;
    private LocalDateTime checkedAt;

    // getters/setters
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long v) { this.employeeId = v; }
    public Long getDeviceItemId() { return deviceItemId; }
    public void setDeviceItemId(Long v) { this.deviceItemId = v; }
    public Boolean getIsEligible() { return isEligible; }
    public void setIsEligible(Boolean v) { this.isEligible = v; }
    public String getReason() { return reason; }
    public void setReason(String v) { this.reason = v; }
    public LocalDateTime getCheckedAt() { return checkedAt; }
    public void setCheckedAt(LocalDateTime v) { this.checkedAt = v; }
}