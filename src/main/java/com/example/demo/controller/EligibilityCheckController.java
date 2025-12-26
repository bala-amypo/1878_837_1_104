package com.example.demo.controller;

import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.service.EligibilityCheckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityCheckController {

    private final EligibilityCheckService service;

    public EligibilityCheckController(EligibilityCheckService service) {
        this.service = service;
    }

    @PostMapping
    public EligibilityCheckRecord checkEligibility(
            @RequestParam Long employeeId,
            @RequestParam Long deviceItemId) {

        return service.validateEligibility(employeeId, deviceItemId);
    }

    @GetMapping("/{employeeId}")
    public List<EligibilityCheckRecord> getChecks(@PathVariable Long employeeId) {
        return service.getChecksByEmployee(employeeId);
    }
}
