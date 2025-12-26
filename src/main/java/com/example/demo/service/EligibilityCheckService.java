package com.example.demo.service;

import com.example.demo.model.EligibilityCheckRecord;
import java.util.List;

public interface EligibilityCheckService {
    EligibilityCheckRecord validateEligibility(Long empId, Long deviceId);
    List<EligibilityCheckRecord> getChecksByEmployee(Long empId);
}
