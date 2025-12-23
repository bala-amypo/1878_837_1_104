package com.example.demo.repository;
import java.util.Optional;

import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
    Optional<EmployeeProfile> findByEmployeeId(String employeeId);
    boolean existsByEmployeeId(String employeeId);

}