package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EligibilityCheckService;

import java.util.List;

public class EligibilityCheckServiceImpl implements EligibilityCheckService {

    private final EmployeeProfileRepository empRepo;
    private final DeviceCatalogItemRepository devRepo;
    private final IssuedDeviceRecordRepository issuedRepo;
    private final PolicyRuleRepository policyRepo;
    private final EligibilityCheckRecordRepository eligibilityRepo;

    public EligibilityCheckServiceImpl(
            EmployeeProfileRepository e,
            DeviceCatalogItemRepository d,
            IssuedDeviceRecordRepository i,
            PolicyRuleRepository p,
            EligibilityCheckRecordRepository el) {
        this.empRepo = e;
        this.devRepo = d;
        this.issuedRepo = i;
        this.policyRepo = p;
        this.eligibilityRepo = el;
    }

    @Override
    public EligibilityCheckRecord validateEligibility(Long empId, Long devId) {
        EligibilityCheckRecord rec = new EligibilityCheckRecord();
        rec.setEmployeeId(empId);
        rec.setDeviceItemId(devId);
        rec.prePersist();

        var empOpt = empRepo.findById(empId);
        var devOpt = devRepo.findById(devId);

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

        if (!issuedRepo.findActiveByEmployeeAndDevice(empId, devId).isEmpty()) {
            rec.setIsEligible(false);
            rec.setReason("Active issuance exists");
            return eligibilityRepo.save(rec);
        }

        if (issuedRepo.countActiveDevicesForEmployee(empId) >= dev.getMaxAllowedPerEmployee()) {
            rec.setIsEligible(false);
            rec.setReason("Maximum allowed devices reached");
            return eligibilityRepo.save(rec);
        }

        for (PolicyRule rule : policyRepo.findByActiveTrue()) {
            boolean deptMatch = rule.getAppliesToDepartment() == null
                    || rule.getAppliesToDepartment().equals(emp.getDepartment());
            boolean roleMatch = rule.getAppliesToRole() == null
                    || rule.getAppliesToRole().equals(emp.getJobRole());

            if (deptMatch && roleMatch && rule.getMaxDevicesAllowed() != null
                    && rule.getMaxDevicesAllowed() <= issuedRepo.countActiveDevicesForEmployee(empId)) {
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
