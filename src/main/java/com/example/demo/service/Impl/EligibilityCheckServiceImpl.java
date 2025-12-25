package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EligibilityCheckRecordRepository eligibilityCheckRecordRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final DeviceCatalogItemRepository deviceCatalogItemRepository;
    private final IssuedDeviceRecordRepository issuedDeviceRecordRepository;
    private final PolicyRuleRepository policyRuleRepository;

    public EligibilityCheckServiceImpl(
            EligibilityCheckRecordRepository eligibilityCheckRecordRepository,
            EmployeeProfileRepository employeeProfileRepository,
            DeviceCatalogItemRepository deviceCatalogItemRepository,
            IssuedDeviceRecordRepository issuedDeviceRecordRepository,
            PolicyRuleRepository policyRuleRepository) {
        this.eligibilityCheckRecordRepository = eligibilityCheckRecordRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.deviceCatalogItemRepository = deviceCatalogItemRepository;
        this.issuedDeviceRecordRepository = issuedDeviceRecordRepository;
        this.policyRuleRepository = policyRuleRepository;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {
        // 1. Check employee exists and is active
        EmployeeProfile employee = employeeProfileRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        if (!employee.getActive()) {
            EligibilityCheckRecord record = new EligibilityCheckRecord(
                    employee, null, false,
                    "Employee is not active and cannot be issued devices");
            return eligibilityCheckRecordRepository.save(record);
        }

        // 2. Check device exists and is active
        DeviceCatalogItem device = deviceCatalogItemRepository.findById(deviceItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + deviceItemId));

        if (!device.getActive()) {
            EligibilityCheckRecord record = new EligibilityCheckRecord(
                    employee, device, false,
                    "Device is inactive and cannot be issued");
            return eligibilityCheckRecordRepository.save(record);
        }

        // 3. Check no active issuance exists for this employee-device combination
        List<IssuedDeviceRecord> activeIssuances = issuedDeviceRecordRepository
                .findActiveByEmployeeAndDevice(employeeId, deviceItemId);

        if (!activeIssuances.isEmpty()) {
            EligibilityCheckRecord record = new EligibilityCheckRecord(
                    employee, device, false,
                    "An active issuance of this device already exists for the employee");
            return eligibilityCheckRecordRepository.save(record);
        }

        // 4. Count active devices for employee and check against
        // device.maxAllowedPerEmployee
        Long activeDeviceCount = issuedDeviceRecordRepository.countActiveDevicesForEmployee(employeeId);

        if (activeDeviceCount >= device.getMaxAllowedPerEmployee()) {
            EligibilityCheckRecord record = new EligibilityCheckRecord(
                    employee, device, false,
                    "Maximum allowed devices of this type already issued. Limit: " +
                            device.getMaxAllowedPerEmployee() + ", Current: " + activeDeviceCount);
            return eligibilityCheckRecordRepository.save(record);
        }

        // 5. Check all active PolicyRules
        List<PolicyRule> activeRules = policyRuleRepository.findByActiveTrue();

        for (PolicyRule rule : activeRules) {
            // Check if rule applies to this employee
            boolean ruleApplies = false;

            if (rule.getAppliesToRole() == null && rule.getAppliesToDepartment() == null) {
                // Global rule applies to all
                ruleApplies = true;
            } else if (rule.getAppliesToRole() != null && rule.getAppliesToRole().equals(employee.getJobRole())) {
                // Rule applies by role
                ruleApplies = true;
            } else if (rule.getAppliesToDepartment() != null &&
                    rule.getAppliesToDepartment().equals(employee.getDepartment())) {
                // Rule applies by department
                ruleApplies = true;
            }

            if (ruleApplies) {
                // Check if employee exceeds maxDevicesAllowed for this rule
                if (activeDeviceCount >= rule.getMaxDevicesAllowed()) {
                    EligibilityCheckRecord record = new EligibilityCheckRecord(
                            employee, device, false,
                            "Policy violation: Rule '" + rule.getRuleCode() + "' limits maximum devices to " +
                                    rule.getMaxDevicesAllowed() + ". Employee currently has " + activeDeviceCount
                                    + " devices");
                    return eligibilityCheckRecordRepository.save(record);
                }
            }
        }

        // All checks passed - employee is eligible
        EligibilityCheckRecord record = new EligibilityCheckRecord(
                employee, device, true,
                "Employee is eligible to receive this device. All policy checks passed.");
        return eligibilityCheckRecordRepository.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return eligibilityCheckRecordRepository.findByEmployeeId(employeeId);
    }

    @Override
    public EligibilityCheckRecord getCheckById(Long checkId) {
        return eligibilityCheckRecordRepository.findById(checkId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Eligibility check record not found with id: " + checkId));
    }
}
