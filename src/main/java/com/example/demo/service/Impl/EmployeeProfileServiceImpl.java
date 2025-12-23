// com/example/demo/service/impl/EmployeeProfileServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;

import java.util.List;
import java.util.Optional;

public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeProfile createEmployee(EmployeeProfile employee) {
        Optional<EmployeeProfile> existingById = repository.findByEmployeeId(employee.getEmployeeId());
        if (existingById.isPresent()) {
            throw new BadRequestException("EmployeeId already exists");
        }
        Optional<EmployeeProfile> existingByEmail = repository.findByEmail(employee.getEmail());
        if (existingByEmail.isPresent()) {
            throw new BadRequestException("Email already exists");
        }
        return repository.save(employee);
    }

    @Override
    public EmployeeProfile getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeProfile> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public EmployeeProfile updateEmployeeStatus(Long id, boolean active) {
        EmployeeProfile employee = getEmployeeById(id);
        employee.setActive(active);
        return repository.save(employee);
    }
}