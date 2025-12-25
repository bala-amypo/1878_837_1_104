// File: src/main/java/com/example/demo/model/EmployeeProfile.java
package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String employeeId;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String jobRole = "STAFF";

    @Column(nullable = false)
    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public EmployeeProfile() {}

    // Getters
    public Long getId() { return id; }
    public String getEmployeeId() { return employeeId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public String getJobRole() { return jobRole; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setDepartment(String department) { this.department = department; }
    public void setJobRole(String jobRole) { this.jobRole = jobRole; }
    public void setActive(boolean active) { this.active = active; }
}