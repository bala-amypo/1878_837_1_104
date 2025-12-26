package com.example.demo.repository;

import com.example.demo.model.EligibilityCheckRecord;
import java.util.*;

public interface EligibilityCheckRecordRepository {
    List<EligibilityCheckRecord> findByEmployeeId(Long empId);
    EligibilityCheckRecord save(EligibilityCheckRecord record);
}
