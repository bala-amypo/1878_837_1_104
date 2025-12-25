// File: src/main/java/com/example/demo/service/impl/IssuedDeviceRecordServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository repository;
    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;

    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository repository,
                                         EmployeeProfileRepository employeeRepo,
                                         DeviceCatalogItemRepository deviceRepo) {
        this.repository = repository;
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {
        EmployeeProfile employee = employeeRepo.findById(record.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        DeviceCatalogItem device = deviceRepo.findById(record.getDeviceItem().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        record.setEmployee(employee);
        record.setDeviceItem(device);
        record.setIssuedDate(LocalDate.now());
        record.setStatus("ISSUED");

        return repository.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {
        IssuedDeviceRecord record = repository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        if ("RETURNED".equals(record.getStatus())) {
            throw new BadRequestException("Device already returned");
        }

        record.setReturnedDate(LocalDate.now());
        record.setStatus("RETURNED");
        return repository.save(record);
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }
}