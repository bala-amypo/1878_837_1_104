// File: src/main/java/com/example/demo/controller/PolicyRuleController.java
package com.example.demo.controller;

import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Policy RulesEndpoints")
@RestController
@RequestMapping("/api/policy-rules")
public class PolicyRuleController {

    private final PolicyRuleService service;

    public PolicyRuleController(PolicyRuleService service) {
        this.service = service;
    }

    @Operation(summary = "Create new policy rule")
    @PostMapping
    public ResponseEntity<PolicyRule> create(@RequestBody PolicyRule rule) {
        PolicyRule created = service.createRule(rule);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "List all policy rules")
    @GetMapping
    public ResponseEntity<List<PolicyRule>> getAll() {
        List<PolicyRule> rules = service.getAllRules();
        return ResponseEntity.ok(rules);
    }

    @Operation(summary = "List only active policy rules")
    @GetMapping("/active")
    public ResponseEntity<List<PolicyRule>> getActive() {
        List<PolicyRule> activeRules = service.getActiveRules();
        return ResponseEntity.ok(activeRules);
    }

    @Operation(summary = "Update policy rule active status")
    @PutMapping("/{id}/active")
    public ResponseEntity<Void> updateActive(@PathVariable Long id, @RequestParam boolean active) {
        // Not in service interface per spec
        throw new UnsupportedOperationException("Update active status not implemented");
    }

    @Operation(summary = "Delete policy rule")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Delete not in service interface
        throw new UnsupportedOperationException("Delete not implemented");
    }
}