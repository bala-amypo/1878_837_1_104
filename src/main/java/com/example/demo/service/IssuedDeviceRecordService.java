package com.example.demo.service;

import com.example.demo.model.IssuedDeviceRecord;

import java.util.List;

public interface IssuedDeviceRecordService {

    int countActiveDeviceForEmployee(Long employeeId);

    List<IssuedDeviceRecord> getDevicesByEmployeeId(Long employeeId);

    IssuedDeviceRecord issueDevice(IssuedDeviceRecord record);

    void deactivateDevice(Long id);
}
