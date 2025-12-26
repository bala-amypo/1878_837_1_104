package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.*;

public class IssuedDeviceRecordServiceImpl {

    private final IssuedDeviceRecordRepository repo;

    public IssuedDeviceRecordServiceImpl(
            IssuedDeviceRecordRepository repo,
            EmployeeProfileRepository e,
            DeviceCatalogItemRepository d) {
        this.repo = repo;
    }

    public IssuedDeviceRecord returnDevice(Long id) {
        IssuedDeviceRecord r = repo.findById(id).orElseThrow();
        if ("RETURNED".equals(r.getStatus()))
            throw new BadRequestException("already returned");
        r.setStatus("RETURNED");
        return repo.save(r);
    }
}
