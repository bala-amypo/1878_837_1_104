package com.example.demo.service.impl;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import com.example.demo.exception.BadRequestException;

public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository repo;

    // 🔴 EXACT constructor expected by tests
    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository repo) {
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
