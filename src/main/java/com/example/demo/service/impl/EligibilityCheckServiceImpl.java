package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EmployeeProfileRepository empRepo;
    private final DeviceCatalogItemRepository devRepo;
    private final IssuedDeviceRecordRepository issuedRepo;
    private final PolicyRuleRepository policyRepo;
    private final EligibilityCheckRecordRepository eligibilityRepo;

    public EligibilityCheckServiceImpl(
            EmployeeProfileRepository empRepo,
            DeviceCatalogItemRepository devRepo,
            IssuedDeviceRecordRepository issuedRepo,
            PolicyRuleRepository policyRepo,
            EligibilityCheckRecordRepository eligibilityRepo) {

        this.empRepo = empRepo;
        this.devRepo = devRepo;
        this.issuedRepo = issuedRepo;
        this.policyRepo = policyRepo;
        this.eligibilityRepo = eligibilityRepo;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long empId, Long deviceId) {

        EligibilityCheckRecord rec = new EligibilityCheckRecord();
        rec.setEmployeeId(empId);
        rec.setDeviceItemId(deviceId);
        rec.prePersist();

        var empOpt = empRepo.findById(empId);
        var devOpt = devRepo.findById(deviceId);

        if (empOpt.isEmpty() || devOpt.isEmpty()) {
            rec.setIsEligible(false);
            rec.setReason("Employee or device not found");
            return eligibilityRepo.save(rec);
        }

        EmployeeProfile emp = empOpt.get();
        DeviceCatalogItem dev = devOpt.get();

        if (!emp.getActive()) {
            rec.setIsEligible(false);
            rec.setReason("Employee not active");
            return eligibilityRepo.save(rec);
        }

        if (!dev.getActive()) {
            rec.setIsEligible(false);
            rec.setReason("Device inactive");
            return eligibilityRepo.save(rec);
        }

        if (!issuedRepo.findActiveByEmployeeAndDevice(empId, deviceId).isEmpty()) {
            rec.setIsEligible(false);
            rec.setReason("Active issuance exists");
            return eligibilityRepo.save(rec);
        }

        if (issuedRepo.countActiveDevicesForEmployee(empId)
                >= dev.getMaxAllowedPerEmployee()) {

            rec.setIsEligible(false);
            rec.setReason("Maximum allowed devices reached");
            return eligibilityRepo.save(rec);
        }

        for (PolicyRule rule : policyRepo.findByActiveTrue()) {
            boolean deptMatch = rule.getAppliesToDepartment() == null
                    || rule.getAppliesToDepartment().equals(emp.getDepartment());
            boolean roleMatch = rule.getAppliesToRole() == null
                    || rule.getAppliesToRole().equals(emp.getJobRole());

            if (deptMatch && roleMatch
                    && rule.getMaxDevicesAllowed() != null
                    && rule.getMaxDevicesAllowed()
                    <= issuedRepo.countActiveDevicesForEmployee(empId)) {

                rec.setIsEligible(false);
                rec.setReason("Policy violation");
                return eligibilityRepo.save(rec);
            }
        }

        rec.setIsEligible(true);
        rec.setReason("Eligible");
        return eligibilityRepo.save(rec);
    }

    @Override
    public List<EligibilityCheckRecord> getChecksByEmployee(Long empId) {
        return eligibilityRepo.findByEmployeeId(empId);
    }
}
