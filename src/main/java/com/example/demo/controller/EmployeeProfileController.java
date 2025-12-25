// File: src/main/java/com/example/demo/controller/EmployeeProfileController.java
package com.example.demo.controller;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "EmployeeProfileEndpoints")
@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    @Operation(summary = "Create new employee profile")
    @PostMapping
    public ResponseEntity<EmployeeProfile> create(@RequestBody EmployeeProfile employee) {
        EmployeeProfile created = service.createEmployee(employee);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Get employee by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProfile> getById(@PathVariable Long id) {
        EmployeeProfile employee = service.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @Operation(summary = "List all employees")
    @GetMapping
    public ResponseEntity<List<EmployeeProfile>> getAll() {
        List<EmployeeProfile> employees = service.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Update employee active status")
    @PutMapping("/{id}/status")
    public ResponseEntity<EmployeeProfile> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        EmployeeProfile updated = service.updateEmployeeStatus(id, active);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete employee profile")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Delete not in service interface per spec
        throw new UnsupportedOperationException("Delete not implemented");
    }
}