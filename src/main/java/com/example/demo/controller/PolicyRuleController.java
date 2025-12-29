package com.example.demo.controller;

import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@Tag(name = "Policy Controller", description = "CRUD operations for Issuance Policies")
public class PolicyRuleController {
    private final PolicyRuleService policyService;

    public PolicyRuleController(PolicyRuleService policyService) {
        this.policyService = policyService;
    }

    @PostMapping
    public ResponseEntity<PolicyRule> createPolicy(@RequestBody PolicyRule rule) {
        return new ResponseEntity<>(policyService.savePolicy(rule), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PolicyRule>> getAllActivePolicies() {
        return ResponseEntity.ok(policyService.getActivePolicies());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivatePolicy(@PathVariable Long id) {
        policyService.deactivatePolicy(id);
        return ResponseEntity.noContent().build();
    }
}