package com.example.demo.service.impl;

import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeProfile create(EmployeeProfile employee) {
        return repository.save(employee);
    }

    @Override
    public EmployeeProfile getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<EmployeeProfile> getAll() {
        return repository.findAll();
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfile employee) {
        EmployeeProfile existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(employee.getName());
            existing.setDepartment(employee.getDepartment());
            existing.setActive(employee.isActive());
            return repository.save(existing);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public EmployeeProfile updateStatus(Long id, boolean active) {
        EmployeeProfile emp = repository.findById(id).orElse(null);
        if (emp != null) {
            emp.setActive(active);
            return repository.save(emp);
        }
        return null;
    }

    @Override
    public boolean existsByEmployeeId(String employeeId) {
        return repository.existsByEmployeeId(employeeId);
    }
}
