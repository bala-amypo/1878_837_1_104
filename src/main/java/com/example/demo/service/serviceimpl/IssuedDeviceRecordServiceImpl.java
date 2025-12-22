package com.example.demo.serviceimpl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository issuedRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;

    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository issuedRepo,
                                         EmployeeProfileRepository employeeRepo,
                                         DeviceCatalogItemRepository deviceRepo) {
        this.issuedRepo = issuedRepo;
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {
        employeeRepo.findById(record.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        deviceRepo.findById(record.getDeviceItem().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if (!issuedRepo.findActiveByEmployeeAndDevice(record.getEmployee().getId(), record.getDeviceItem().getId()).isEmpty()) {
            throw new BadRequestException("Active issuance exists");
        }

        record.setIssuedDate(LocalDate.now());
        record.setStatus("ISSUED");
        return issuedRepo.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {
        IssuedDeviceRecord record = issuedRepo.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        if ("RETURNED".equals(record.getStatus())) {
            throw new BadRequestException("already returned");
        }

        record.setStatus("RETURNED");
        record.setReturnedDate(LocalDate.now());
        return issuedRepo.save(record);
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return issuedRepo.findByEmployeeId(employeeId);
    }
}