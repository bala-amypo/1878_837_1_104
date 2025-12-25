// File: src/main/java/com/example/demo/service/impl/EligibilityCheckServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EmployeeProfileRepository employeeRepo;
    private final DeviceCatalogItemRepository deviceRepo;
    private final IssuedDeviceRecordRepository issuedRepo;
    private final PolicyRuleRepository policyRepo;
    private final EligibilityCheckRecordRepository checkRepo;

    public EligibilityCheckServiceImpl(EmployeeProfileRepository employeeRepo,
                                       DeviceCatalogItemRepository deviceRepo,
                                       IssuedDeviceRecordRepository issuedRepo,
                                       PolicyRuleRepository policyRepo,
                                       EligibilityCheckRecordRepository checkRepo) {
        this.employeeRepo = employeeRepo;
        this.deviceRepo = deviceRepo;
        this.issuedRepo = issuedRepo;
        this.policyRepo = policyRepo;
        this.checkRepo = checkRepo;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {
        EmployeeProfile employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        DeviceCatalogItem device = deviceRepo.findById(deviceItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if (!employee.isActive()) {
            return createRecord(employee, device, false, "Employee not active");
        }

        if (!device.isActive()) {
            return createRecord(employee, device, false, "Device inactive");
        }

        // Check no active issuance exists for this employee-device
        boolean hasActiveIssuance = issuedRepo.findActiveByEmployeeAndDevice(employeeId, deviceItemId).size() > 0;
        if (hasActiveIssuance) {
            return createRecord(employee, device, false, "Active issuance exists");
        }

        // Check per-device limit
        long currentActiveCount = issuedRepo.countActiveDevicesForEmployee(employeeId);
        if (currentActiveCount + 1 > device.getMaxAllowedPerEmployee()) {
            return createRecord(employee, device, false, "Maximum allowed devices reached for this device type");
        }

        // Check policy rules
        List<PolicyRule> activeRules = policyRepo.findByActiveTrue();
        for (PolicyRule rule : activeRules) {
            boolean applies = (rule.getAppliesToRole() == null || rule.getAppliesToRole().equals(employee.getJobRole())) &&
                              (rule.getAppliesToDepartment() == null || rule.getAppliesToDepartment().equals(employee.getDepartment()));

            if (applies && currentActiveCount + 1 > rule.getMaxDevicesAllowed()) {
                return createRecord(employee, device, false, "Policy violation: Maximum devices allowed by rule '" + rule.getRuleCode() + "'");
            }
        }

        return createRecord(employee, device, true, "Eligible for device issuance");
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return checkRepo.findByEmployeeId(employeeId);
    }

    private EligibilityCheckRecord createRecord(EmployeeProfile employee, DeviceCatalogItem device,
                                                boolean eligible, String reason) {
        EligibilityCheckRecord record = new EligibilityCheckRecord();
        record.setEmployee(employee);
        record.setDeviceItem(device);
        record.setEligible(eligible);
        record.setReason(reason);
        // checkedAt auto-populated by @PrePersist
        return checkRepo.save(record);
    }
}