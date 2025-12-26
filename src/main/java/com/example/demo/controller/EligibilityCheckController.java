package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityCheckController {

    @PostMapping
    public String checkEligibility(@RequestParam Long employeeId,
                                   @RequestParam Long deviceId) {
        return "Eligibility checked for employee "
                + employeeId + " and device " + deviceId;
    }
}
