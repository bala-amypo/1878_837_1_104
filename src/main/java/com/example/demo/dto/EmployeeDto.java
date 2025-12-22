package com.example.demo.dto;

public class EmployeeDto {
    private String employeeId;
    private String fullName;
    private String email;
    private String department;
    private String jobRole;
    private Boolean active;

    // getters/setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String v) { this.employeeId = v; }
    public String getFullName() { return fullName; }
    public void setFullName(String v) { this.fullName = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getDepartment() { return department; }
    public void setDepartment(String v) { this.department = v; }
    public String getJobRole() { return jobRole; }
    public void setJobRole(String v) { this.jobRole = v; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean v) { this.active = v; }
}