package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String employeeId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String jobRole; // ADMIN / DEVELOPER / MANAGER / STAFF

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.jobRole == null || this.jobRole.trim().isEmpty()) {
            this.jobRole = "STAFF";
        }
        if (this.active == null) {
            this.active = true;
        }
    }

    public EmployeeProfile(String employeeId, String fullName, String email, String department, String jobRole) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.jobRole = jobRole != null && !jobRole.trim().isEmpty() ? jobRole : "STAFF";
        this.active = true;
    }
}
