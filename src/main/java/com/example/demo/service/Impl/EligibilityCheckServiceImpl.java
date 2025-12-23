package com.example.demo.service.Impl;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.repository.EligibilityCheckRecordRepository;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EligibilityCheckRecordRepository repository;

    public EligibilityCheckServiceImpl(EligibilityCheckRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public EligibilityCheckRecord save(EligibilityCheckRecord record) {
        return repository.save(record);
    }

    @Override
    public EligibilityCheckRecord validate(Long employeeId, Long deviceItemId) {
        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);

        // Simple example: mark eligible if deviceId is even, else not eligible
        record.setEligible(deviceItemId % 2 == 0);

        return repository.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getAll() {
        return repository.findAll();
    }

    @Override
    public List<EligibilityCheckRecord> getByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }
}
