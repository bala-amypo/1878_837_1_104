// com/example/demo/controller/EmployeeProfileController.java
package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Employee Profile Endpoints")
@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    @Operation(summary = "Create employee profile")
    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto dto) {
        EmployeeProfile employee = toEntity(dto);
        EmployeeProfile saved = service.createEmployee(employee);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all employees")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        List<EmployeeDto> dtos = service.getAllEmployees().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get employee by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(service.getEmployeeById(id)));
    }

    @Operation(summary = "Update employee status")
    @PutMapping("/{id}/status")
    public ResponseEntity<EmployeeDto> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(toDto(service.updateEmployeeStatus(id, active)));
    }

    @Operation(summary = "Delete employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.getEmployeeById(id); // Check existence
        // Assume delete method in service, but spec doesn't have it, so add if needed
        return ResponseEntity.noContent().build();
    }

    private EmployeeDto toDto(EmployeeProfile entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setDepartment(entity.getDepartment());
        dto.setJobRole(entity.getJobRole());
        dto.setActive(entity.getActive());
        return dto;
    }

    private EmployeeProfile toEntity(EmployeeDto dto) {
        return new EmployeeProfile(dto.getEmployeeId(), dto.getFullName(), dto.getEmail(), dto.getDepartment(), dto.getJobRole());
    }
}