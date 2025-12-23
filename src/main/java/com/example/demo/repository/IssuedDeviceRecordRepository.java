// com/example/demo/repository/IssuedDeviceRecordRepository.java
package com.example.demo.repository;

import com.example.demo.model.IssuedDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssuedDeviceRecordRepository extends JpaRepository<IssuedDeviceRecord, Long> {

    @Query("SELECT r FROM IssuedDeviceRecord r WHERE r.employee.id = ?1 AND r.deviceItem.id = ?2 AND r.status = 'ISSUED'")
    List<IssuedDeviceRecord> findActiveByEmployeeAndDevice(Long employeeId, Long deviceItemId);

    @Query("SELECT COUNT(r) FROM IssuedDeviceRecord r WHERE r.employee.id = ?1 AND r.status = 'ISSUED'")
    Long countActiveDevicesForEmployee(Long employeeId);

    List<IssuedDeviceRecord> findByEmployeeId(Long employeeId);

    List<IssuedDeviceRecord> findAll();
}