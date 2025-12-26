package com.example.demo.controller;

import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policy-rules")
@Tag(name = "Policy Rules Endpoints", description = "Manage policy rules for device issuance")
@SecurityRequirement(name = "bearerAuth")
public class PolicyRuleController {

    private final PolicyRuleService policyRuleService;

    public PolicyRuleController(PolicyRuleService policyRuleService) {
        this.policyRuleService = policyRuleService;
    }

    @PostMapping
    @Operation(summary = "Create a new policy rule")
    public ResponseEntity<PolicyRule> createRule(@RequestBody PolicyRule rule) {
        PolicyRule created = policyRuleService.createRule(rule);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Get all policy rules")
    public ResponseEntity<List<PolicyRule>> getAllRules() {
        List<PolicyRule> rules = policyRuleService.getAllRules();
        return ResponseEntity.ok(rules);
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active policy rules")
    public ResponseEntity<List<PolicyRule>> getActiveRules() {
        List<PolicyRule> activeRules = policyRuleService.getActiveRules();
        return ResponseEntity.ok(activeRules);
    }

    @PutMapping("/{id}/active")
    @Operation(summary = "Update policy rule active status")
    public ResponseEntity<PolicyRule> updateRuleActiveStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        PolicyRule updated = policyRuleService.updateActiveStatus(id, active);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a policy rule")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        policyRuleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}
