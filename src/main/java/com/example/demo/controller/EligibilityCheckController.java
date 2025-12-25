// File: src/main/java/com/example/demo/controller/EligibilityCheckController.java
package com.example.demo.controller;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.service.EligibilityCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Eligibility Check Endpoints")
@RestController
@RequestMapping("/api/eligibility")
public class EligibilityCheckController {

    private final EligibilityCheckService service;

    public EligibilityCheckController(EligibilityCheckService service) {
        this.service = service;
    }

    @Operation(summary = "Validate eligibility for device issuance")
    @PostMapping("/validate/{employeeId}/{deviceItemId}")
    public ResponseEntity<EligibilityCheckRecord> validateEligibility(
            @PathVariable Long employeeId,
            @PathVariable Long deviceItemId) {
        EligibilityCheckRecord record = service.validateEligibility(employeeId, deviceItemId);
        return ResponseEntity.ok(record);
    }

    @Operation(summary = "Get all eligibility checks for an employee")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EligibilityCheckRecord>> getChecksByEmployee(@PathVariable Long employeeId) {
        List<EligibilityCheckRecord> checks = service.getChecksByEmployee(employeeId);
        return ResponseEntity.ok(checks);
    }

    
    @Operation(summary = "Get specific eligibility check record")
    @GetMapping("/{checkId}")
    public ResponseEntity<EligibilityCheckRecord> getCheckById(@PathVariable Long checkId) {
        throw new UnsupportedOperationException("Get by check ID not implemented");
    }
}