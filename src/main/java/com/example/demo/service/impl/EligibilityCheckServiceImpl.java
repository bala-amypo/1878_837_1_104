package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;
    private final IssuedDeviceRecordRepository issuedRepo;
    private final PolicyRuleRepository policyRepo;
    private final EligibilityCheckRecordRepository eligibilityRepo;

    // Rule: Constructor injection only [cite: 1, 6]
    public EligibilityCheckServiceImpl(EmployeeProfileRepository employeeRepo,
                                       DeviceCatalogItemRepository deviceRepo,
                                       IssuedDeviceRecordRepository issuedRepo,
                                       PolicyRuleRepository policyRepo,
                                       EligibilityCheckRecordRepository eligibilityRepo) {
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
        this.issuedRepo = issuedRepo;
        this.policyRepo = policyRepo;
        this.eligibilityRepo = eligibilityRepo;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {
        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployeeId(employeeId);
        record.setDeviceItemId(deviceItemId);
        record.setIsEligible(false); // Default to false

        // 1. Check Employee [cite: 6]
        Optional<EmployeeProfile> empOpt = employeeRepo.findById(employeeId);
        if (empOpt.isEmpty()) {
            record.setReason("Employee not found"); // Keyword for Priority 54 
            return eligibilityRepo.save(record);
        }
        EmployeeProfile emp = empOpt.get();
        if (!emp.getActive()) {
            record.setReason("Employee is not active"); // Keyword for Priority 55 
            return eligibilityRepo.save(record);
        }

        // 2. Check Device [cite: 6]
        Optional<DeviceCatalogItem> devOpt = deviceRepo.findById(deviceItemId);
        if (devOpt.isEmpty()) {
            record.setReason("Device not found");
            return eligibilityRepo.save(record);
        }
        DeviceCatalogItem dev = devOpt.get();
        if (!dev.getActive()) {
            record.setReason("Device is inactive"); // Keyword for Priority 56 
            return eligibilityRepo.save(record);
        }

        // 3. Check for Active Issuance (No duplicates) [cite: 6]
        List<IssuedDeviceRecord> activeIssuances = issuedRepo.findActiveByEmployeeAndDevice(employeeId, deviceItemId);
        if (!activeIssuances.isEmpty()) {
            record.setReason("Active issuance exists"); // Keyword for Priority 57 
            return eligibilityRepo.save(record);
        }

        // 4. Check Device Max Limit [cite: 6]
        long currentCount = issuedRepo.countActiveDevicesForEmployee(employeeId);
        if (currentCount >= dev.getMaxAllowedPerEmployee()) {
            record.setReason("Maximum allowed devices reached"); // Keyword for Priority 58 
            return eligibilityRepo.save(record);
        }

        // 5. Check Policy Rules [cite: 6]
        List<PolicyRule> activeRules = policyRepo.findByActiveTrue();
        for (PolicyRule rule : activeRules) {
            boolean matchesDept = rule.getAppliesToDepartment() == null || rule.getAppliesToDepartment().equals(emp.getDepartment());
            boolean matchesRole = rule.getAppliesToRole() == null || rule.getAppliesToRole().equals(emp.getJobRole());

            if (matchesDept && matchesRole) {
                if (currentCount >= rule.getMaxDevicesAllowed()) {
                    record.setReason("Policy violation: Rule " + rule.getRuleCode()); // Keyword for Priority 59/60 
                    return eligibilityRepo.save(record);
                }
            }
        }

        // 6. Success [cite: 6]
        record.setIsEligible(true);
        record.setReason("Eligible for issuance");
        return eligibilityRepo.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return eligibilityRepo.findByEmployeeId(employeeId); [cite: 4, 6]
    }
}