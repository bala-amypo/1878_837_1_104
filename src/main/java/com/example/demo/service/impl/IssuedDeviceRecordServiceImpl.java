// com/example/demo/service/impl/IssuedDeviceRecordServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;

import java.time.LocalDate;
import java.util.List;

public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository repository;
    private final EmployeeProfileRepository employeeRepository;
    private final DeviceCatalogItemRepository deviceRepository;

    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository repository,
                                         EmployeeProfileRepository employeeRepository,
                                         DeviceCatalogItemRepository deviceRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {
        EmployeeProfile employee = employeeRepository.findById(record.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        DeviceCatalogItem device = deviceRepository.findById(record.getDeviceItem().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        List<IssuedDeviceRecord> active = repository.findActiveByEmployeeAndDevice(employee.getId(), device.getId());
        if (!active.isEmpty()) {
            throw new BadRequestException("active issuance");
        }

        record.setEmployee(employee);
        record.setDeviceItem(device);
        record.setIssuedDate(LocalDate.now());
        record.setStatus("ISSUED");
        return repository.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {
        IssuedDeviceRecord record = repository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Issued device record not found"));
        if (record.getReturnedDate() != null) {
            throw new BadRequestException("already returned");
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