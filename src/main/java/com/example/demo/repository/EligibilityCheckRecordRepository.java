package com.example.demo.repository;

import com.example.demo.model.EligibilityCheckRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EligibilityCheckRecordRepository extends JpaRepository<EligibilityCheckRecord, Long> {

    @Query("SELECT e FROM EligibilityCheckRecord e WHERE e.employee.id = :employeeId")
    List<EligibilityCheckRecord> findByEmployeeId(@Param("employeeId") Long employeeId);
}
