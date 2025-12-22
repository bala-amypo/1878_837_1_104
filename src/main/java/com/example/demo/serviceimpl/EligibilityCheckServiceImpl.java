package com.example.demo.serviceimpl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;

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

        StringBuilder reason = new StringBuilder();
        boolean eligible = true;

        if (!employee.getActive()) {
            eligible = false;
            reason.append("Employee not active. ");
        }

        if (!device.getActive()) {
            eligible = false;
            reason.append("Device inactive. ");
        }

        if (!issuedRepo.findActiveByEmployeeAndDevice(employeeId, deviceItemId).isEmpty()) {
            eligible = false;
            reason.append("Active issuance exists. ");
        }

        long activeDeviceCount = issuedRepo.countActiveDevicesForEmployee(employeeId);
        if (activeDeviceCount >= device.getMaxAllowedPerEmployee()) {
            eligible = false;
            reason.append("Maximum allowed devices reached for this device type. ");
        }

        List<PolicyRule> activeRules = policyRepo.findByActiveTrue();
        for (PolicyRule rule : activeRules) {
            boolean ruleApplies = (rule.getAppliesToRole() == null || rule.getAppliesToRole().equals(employee.getJobRole())) &&
                                  (rule.getAppliesToDepartment() == null || rule.getAppliesToDepartment().equals(employee.getDepartment()));

            if (ruleApplies && activeDeviceCount >= rule.getMaxDevicesAllowed()) {
                eligible = false;
                reason.append("Policy violation: ").append(rule.getDescription() != null ? rule.getDescription() : rule.getRuleCode())
                      .append(". Maximum allowed devices: ").append(rule.getMaxDevicesAllowed()).append(". ");
            }
        }

        if (reason.length() == 0) {
            reason.append("Employee is eligible for device issuance.");
        }

        EligibilityCheckRecord check = new EligibilityCheckRecord(employee, device, eligible, reason.toString());
        return checkRepo.save(check);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return checkRepo.findByEmployeeId(employeeId);
    }
}