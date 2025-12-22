package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Profile Endpoints")
public class EmployeeProfileController {

    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create new employee profile")
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto dto) {
        EmployeeProfile entity = mapToEntity(dto);
        EmployeeProfile saved = service.createEmployee(entity);
        return ResponseEntity.ok(mapToDto(saved));
    }

    @GetMapping
    @Operation(summary = "List all employees")
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return ResponseEntity.ok(service.getAllEmployees().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapToDto(service.getEmployeeById(id)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update employee active status")
    public ResponseEntity<EmployeeDto> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(mapToDto(service.updateEmployeeStatus(id, active)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee profile")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.updateEmployeeStatus(id, false); // soft delete via active = false
        return ResponseEntity.noContent().build();
    }

    private EmployeeDto mapToDto(EmployeeProfile entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setDepartment(entity.getDepartment());
        dto.setJobRole(entity.getJobRole());
        dto.setActive(entity.getActive());
        return dto;
    }

    private EmployeeProfile mapToEntity(EmployeeDto dto) {
        EmployeeProfile entity = new EmployeeProfile();
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setDepartment(dto.getDepartment());
        entity.setJobRole(dto.getJobRole());
        entity.setActive(dto.getActive());
        return entity;
    }
}