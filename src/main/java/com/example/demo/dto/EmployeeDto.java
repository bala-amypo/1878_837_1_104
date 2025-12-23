package com.example.demo.dto;
public record EmployeeDto(String employeeId, String fullName, String email,
                         String department, String jobRole, Boolean active) {}