package com.example.demo.controller;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.service.EligibilityCheckService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eligibility")
@Tag(name = "Eligibility Controller", description = "Endpoint for checking equipment issuance eligibility")
public class EligibilityCheckController {
    private final EligibilityCheckService eligibilityService;

    public EligibilityCheckController(EligibilityCheckService eligibilityService) {
        this.eligibilityService = eligibilityService;
    }

    @PostMapping("/check")
    public ResponseEntity<EligibilityCheckRecord> validateEligibility(
            @RequestParam Long employeeId, 
            @RequestParam Long deviceId) {
        return ResponseEntity.ok(eligibilityService.validateEligibility(employeeId, deviceId));
    }

    @GetMapping("/history/{employeeId}")
    public ResponseEntity<List<EligibilityCheckRecord>> getHistory(@PathVariable Long employeeId) {
        return ResponseEntity.ok(eligibilityService.getCheckHistoryByEmployee(employeeId));
    }
}