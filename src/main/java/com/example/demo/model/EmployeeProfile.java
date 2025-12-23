package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_profiles",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "employeeId"),
           @UniqueConstraint(columnNames = "email")
       })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeeProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String jobRole = "STAFF"; // default

    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (jobRole == null) jobRole = "STAFF";
    }
}