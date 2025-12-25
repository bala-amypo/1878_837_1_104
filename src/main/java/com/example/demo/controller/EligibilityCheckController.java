package com.example.demo.controller;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.service.EligibilityCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eligibility")
@Tag(name = "Eligibility Check Endpoints", description = "Validate employee eligibility for device issuance")
@SecurityRequirement(name = "bearerAuth")
public class EligibilityCheckController {

    private final EligibilityCheckService eligibilityCheckService;

    public EligibilityCheckController(EligibilityCheckService eligibilityCheckService) {
        this.eligibilityCheckService = eligibilityCheckService;
    }

    @PostMapping("/validate/{employeeId}/{deviceItemId}")
    @Operation(summary = "Validate employee eligibility to receive a device")
    public ResponseEntity<EligibilityCheckRecord> validateEligibility(
            @PathVariable Long employeeId,
            @PathVariable Long deviceItemId) {
        EligibilityCheckRecord check = eligibilityCheckService.validateEligibility(employeeId, deviceItemId);
        return ResponseEntity.ok(check);
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get all eligibility checks for an employee")
    public ResponseEntity<List<EligibilityCheckRecord>> getChecksByEmployee(@PathVariable Long employeeId) {
        List<EligibilityCheckRecord> checks = eligibilityCheckService.getChecksByEmployee(employeeId);
        return ResponseEntity.ok(checks);
    }

    @GetMapping("/{checkId}")
    @Operation(summary = "Get specific eligibility check record")
    public ResponseEntity<EligibilityCheckRecord> getCheckById(@PathVariable Long checkId) {
        EligibilityCheckRecord check = eligibilityCheckService.getCheckById(checkId);
        return ResponseEntity.ok(check);
    }
}
