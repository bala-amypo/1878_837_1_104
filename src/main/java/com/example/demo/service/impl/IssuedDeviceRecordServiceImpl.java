package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.service.IssuedDeviceRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IssuedDeviceRecordServiceImpl implements IssuedDeviceRecordService {

    private final IssuedDeviceRecordRepository issuedDeviceRecordRepository;

    public IssuedDeviceRecordServiceImpl(IssuedDeviceRecordRepository issuedDeviceRecordRepository) {
        this.issuedDeviceRecordRepository = issuedDeviceRecordRepository;
    }

    @Override
    public IssuedDeviceRecord issueDevice(IssuedDeviceRecord record) {
        // Set issuedDate and status
        if (record.getIssuedDate() == null) {
            record.setIssuedDate(LocalDate.now());
        }
        record.setStatus("ISSUED");

        return issuedDeviceRecordRepository.save(record);
    }

    @Override
    public IssuedDeviceRecord returnDevice(Long recordId) {
        IssuedDeviceRecord record = getRecordById(recordId);

        // Check if already returned
        if ("RETURNED".equals(record.getStatus())) {
            throw new BadRequestException("Device already returned for this record");
        }

        record.setReturnedDate(LocalDate.now());
        record.setStatus("RETURNED");

        return issuedDeviceRecordRepository.save(record);
    }

    @Override
    public List<IssuedDeviceRecord> getIssuedDevicesByEmployee(Long employeeId) {
        return issuedDeviceRecordRepository.findByEmployeeId(employeeId);
    }

    @Override
    public IssuedDeviceRecord getRecordById(Long id) {
        return issuedDeviceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issued device record not found with id: " + id));
    }
}
