package com.example.demo.service.impl;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    @Autowired
    private IssuedDeviceRecordRepository repository;

    @Override
    public int countActiveDeviceForEmployee(Long employeeId) {
        return repository.countActiveDeviceForEmployee(employeeId);
    }

    @Override
    public List<IssuedDeviceRecord> getDevicesByEmployeeId(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {
        record.setActive(true);
        return repository.save(record);
    }

    @Override
    public void deactivateDevice(Long id) {
        IssuedDeviceRecord record = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device record not found"));
        record.setActive(false);
        repository.save(record);
    }
}
