package com.example.demo.controller;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Profile Endpoints", description = "Manage employee profiles")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

    @PostMapping
    @Operation(summary = "Create a new employee profile")
    public ResponseEntity<EmployeeProfile> createEmployee(@RequestBody EmployeeProfile employee) {
        EmployeeProfile created = employeeProfileService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Get all employee profiles")
    public ResponseEntity<List<EmployeeProfile>> getAllEmployees() {
        List<EmployeeProfile> employees = employeeProfileService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee profile by ID")
    public ResponseEntity<EmployeeProfile> getEmployeeById(@PathVariable Long id) {
        EmployeeProfile employee = employeeProfileService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update employee active status")
    public ResponseEntity<EmployeeProfile> updateEmployeeStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        EmployeeProfile updated = employeeProfileService.updateEmployeeStatus(id, active);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee profile")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeProfileService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
