package com.example.demo.repository;

import com.example.demo.model.IssuedDeviceRecord;
import java.util.*;

public interface IssuedDeviceRecordRepository {
    Optional<IssuedDeviceRecord> findById(Long id);
    List<IssuedDeviceRecord> findActiveByEmployeeAndDevice(Long empId, Long deviceId);
    long countActiveDevicesForEmployee(Long empId);
    IssuedDeviceRecord save(IssuedDeviceRecord record);
}
