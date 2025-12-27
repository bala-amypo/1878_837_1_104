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

    // Requirement: Constructor injection only 
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
        record.setIsEligible(false);

        // 1. Employee Check 
        Optional<EmployeeProfile> empOpt = employeeRepo.findById(employeeId);
        if (empOpt.isEmpty()) {
            record.setReason("Employee not found"); // Required keyword 
            return eligibilityRepo.save(record);
        }
        EmployeeProfile emp = empOpt.get();
        if (!emp.getActive()) {
            record.setReason("Employee is not active"); // Required keyword 
            return eligibilityRepo.save(record);
        }

        // 2. Device Check 
        Optional<DeviceCatalogItem> devOpt = deviceRepo.findById(deviceItemId);
        if (devOpt.isEmpty()) {
            record.setReason("Device not found");
            return eligibilityRepo.save(record);
        }
        DeviceCatalogItem dev = devOpt.get();
        if (!dev.getActive()) {
            record.setReason("Device is inactive"); // Required keyword 
            return eligibilityRepo.save(record);
        }

        // 3. Duplicate Active Issuance Check 
        List<IssuedDeviceRecord> activeIssuances = issuedRepo.findActiveByEmployeeAndDevice(employeeId, deviceItemId);
        if (!activeIssuances.isEmpty()) {
            record.setReason("Active issuance exists"); // Required keyword 
            return eligibilityRepo.save(record);
        }

        // 4. Device-Level Max Limit Check 
        long currentCount = issuedRepo.countActiveDevicesForEmployee(employeeId);
        if (currentCount >= dev.getMaxAllowedPerEmployee()) {
            record.setReason("Maximum allowed devices reached"); // Required keyword 
            return eligibilityRepo.save(record);
        }

        // 5. Policy Rule Validation 
        List<PolicyRule> activeRules = policyRepo.findByActiveTrue();
        for (PolicyRule rule : activeRules) {
            boolean matchesDept = rule.getAppliesToDepartment() == null || rule.getAppliesToDepartment().equals(emp.getDepartment());
            boolean matchesRole = rule.getAppliesToRole() == null || rule.getAppliesToRole().equals(emp.getJobRole());

            if (matchesDept && matchesRole) {
                if (currentCount >= rule.getMaxDevicesAllowed()) {
                    record.setReason("Policy violation: " + rule.getRuleCode()); // Required keyword 
                    return eligibilityRepo.save(record);
                }
            }
        }

        // 6. Success 
        record.setIsEligible(true);
        record.setReason("Eligible for issuance");
        return eligibilityRepo.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return eligibilityRepo.findByEmployeeId(employeeId);
    }
}