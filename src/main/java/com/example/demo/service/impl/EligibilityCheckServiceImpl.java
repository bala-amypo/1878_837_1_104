// com/example/demo/service/impl/EligibilityCheckServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.PolicyRule;
import com.example.demo.repository.DeviceCatalogItemRepository;
import com.example.demo.repository.EligibilityCheckRecordRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.IssuedDeviceRecordRepository;
import com.example.demo.repository.PolicyRuleRepository;
import com.example.demo.service.EligibilityCheckService;

import java.util.List;

public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EligibilityCheckRecordRepository repository;
    private final EmployeeProfileRepository employeeRepository;
    private final DeviceCatalogItemRepository deviceRepository;
    private final IssuedDeviceRecordRepository issuedRepository;
    private final PolicyRuleRepository policyRepository;

    public EligibilityCheckServiceImpl(EligibilityCheckRecordRepository repository,
                                       EmployeeProfileRepository employeeRepository,
                                       DeviceCatalogItemRepository deviceRepository,
                                       IssuedDeviceRecordRepository issuedRepository,
                                       PolicyRuleRepository policyRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.deviceRepository = deviceRepository;
        this.issuedRepository = issuedRepository;
        this.policyRepository = policyRepository;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long employeeId, Long deviceItemId) {
        EmployeeProfile employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        if (!employee.getActive()) {
            return createRecord(employee, null, false, "not active");
        }

        DeviceCatalogItem device = deviceRepository.findById(deviceItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        if (!device.getActive()) {
            return createRecord(employee, device, false, "inactive");
        }

        List<IssuedDeviceRecord> activeIssuances = issuedRepository.findActiveByEmployeeAndDevice(employeeId, deviceItemId);
        if (!activeIssuances.isEmpty()) {
            return createRecord(employee, device, false, "active issuance");
        }

        Long activeCount = issuedRepository.countActiveDevicesForEmployee(employeeId);
        if (activeCount >= device.getMaxAllowedPerEmployee()) {
            return createRecord(employee, device, false, "Maximum allowed devices");
        }

        List<PolicyRule> activeRules = policyRepository.findByActiveTrue();
        for (PolicyRule rule : activeRules) {
            boolean applies = (rule.getAppliesToRole() == null || rule.getAppliesToRole().equals(employee.getJobRole())) &&
                              (rule.getAppliesToDepartment() == null || rule.getAppliesToDepartment().equals(employee.getDepartment()));
            if (applies && activeCount >= rule.getMaxDevicesAllowed()) {
                return createRecord(employee, device, false, "Policy violation");
            }
        }

        return createRecord(employee, device, true, "Eligible");
    }

    private EligibilityCheckRecord createRecord(EmployeeProfile employee, DeviceCatalogItem device, boolean eligible, String reason) {
        EligibilityCheckRecord record = new EligibilityCheckRecord(employee, device, eligible, reason);
        return repository.save(record);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }
}