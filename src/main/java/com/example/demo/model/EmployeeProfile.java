package com.example.demo.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
public class EmployeeProfile {
    private Long id;
    private String employeeId;
    private String fullName;
    private String email;
    private String department;
    private String jobRole;
    private Boolean active = true;
    private LocalDateTime createdAt;
}
