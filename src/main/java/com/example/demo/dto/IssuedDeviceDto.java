package com.example.demo.dto;

import java.time.LocalDate;

public class IssuedDeviceDto {
    private Long employeeId;
    private Long deviceItemId;
    private LocalDate issuedDate;
    private LocalDate returnedDate;
    private String status;

    // getters/setters
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long v) { this.employeeId = v; }
    public Long getDeviceItemId() { return deviceItemId; }
    public void setDeviceItemId(Long v) { this.deviceItemId = v; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate v) { this.issuedDate = v; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate v) { this.returnedDate = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
}