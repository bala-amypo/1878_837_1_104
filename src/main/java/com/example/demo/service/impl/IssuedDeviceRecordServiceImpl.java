package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;

public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository repo;

    // 🔥 EXACT constructor expected by test case
    public IssuedDeviceRecordServiceImpl(
            IssuedDeviceRecordRepository repo,
            EmployeeProfileRepository employeeRepo,
            DeviceCatalogItemRepository deviceRepo) {

        this.repo = repo;
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long id) {
        IssuedDeviceRecord record = repo.findById(id).orElseThrow();

        if ("RETURNED".equals(record.getStatus())) {
            throw new BadRequestException("already returned");
        }

        record.setStatus("RETURNED");
        return repo.save(record);
    }
}
