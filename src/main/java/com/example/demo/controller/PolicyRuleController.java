// com/example/demo/controller/PolicyRuleController.java
package com.example.demo.controller;

import com.example.demo.dto.PolicyRuleDto;
import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Policy Rules Endpoints")
@RestController
@RequestMapping("/api/policy-rules")
public class PolicyRuleController {

    private final PolicyRuleService service;

    public PolicyRuleController(PolicyRuleService service) {
        this.service = service;
    }

    @Operation(summary = "Create policy rule")
    @PostMapping
    public ResponseEntity<PolicyRuleDto> create(@RequestBody PolicyRuleDto dto) {
        PolicyRule rule = toEntity(dto);
        PolicyRule saved = service.createRule(rule);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all policy rules")
    @GetMapping
    public ResponseEntity<List<PolicyRuleDto>> getAll() {
        List<PolicyRuleDto> dtos = service.getAllRules().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get active policy rules")
    @GetMapping("/active")
    public ResponseEntity<List<PolicyRuleDto>> getActive() {
        List<PolicyRuleDto> dtos = service.getActiveRules().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Update policy rule active status")
    @PutMapping("/{id}/active")
    public ResponseEntity<PolicyRuleDto> updateActive(@PathVariable Long id, @RequestParam boolean active) {
        // Assume updateActive in service
        return ResponseEntity.ok(toDto(/* service.updateActive(id, active) */ null)); // Placeholder
    }

    @Operation(summary = "Delete policy rule")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Assume delete in service
        return ResponseEntity.noContent().build();
    }

    private PolicyRuleDto toDto(PolicyRule entity) {
        PolicyRuleDto dto = new PolicyRuleDto();
        dto.setRuleCode(entity.getRuleCode());
        dto.setDescription(entity.getDescription());
        dto.setAppliesToRole(entity.getAppliesToRole());
        dto.setAppliesToDepartment(entity.getAppliesToDepartment());
        dto.setMaxDevicesAllowed(entity.getMaxDevicesAllowed());
        dto.setActive(entity.getActive());
        return dto;
    }

    private PolicyRule toEntity(PolicyRuleDto dto) {
        return new PolicyRule(dto.getRuleCode(), dto.getDescription(), dto.getAppliesToRole(), dto.getAppliesToDepartment(), dto.getMaxDevicesAllowed());
    }
}