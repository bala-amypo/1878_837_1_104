package com.example.demo.repository;

import com.example.demo.model.IssuedDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssuedDeviceRecordRepository extends JpaRepository<IssuedDeviceRecord, Long> {
    List<IssuedDeviceRecord> findActiveByEmployeeAndDevice(Long employeeId, Long deviceItemId);
    long countActiveDevicesForEmployee(Long employeeId);
    List<IssuedDeviceRecord> findByEmployeeId(Long employeeId);
    List<IssuedDeviceRecord> findAll();
}