package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;
import java.util.*;

public interface EmployeeProfileRepository {
    Optional<EmployeeProfile> findByEmployeeId(String employeeId);
    Optional<EmployeeProfile> findByEmail(String email);
    Optional<EmployeeProfile> findById(Long id);
    List<EmployeeProfile> findAll();
    EmployeeProfile save(EmployeeProfile emp);
}
