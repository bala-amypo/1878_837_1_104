package com.example.demo.repository;

import com.example.demo.model.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
    Optional<EmployeeProfile> findByEmployeeId(String employeeId);
    Optional<EmployeeProfile> findByEmail(String email);
    List<EmployeeProfile> findAll();
}