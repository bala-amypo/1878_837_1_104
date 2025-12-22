package com.example.demo.controller;

import com.example.demo.dto.PolicyRuleDto;
import com.example.demo.model.PolicyRule;
import com.example.demo.service.PolicyRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/policy-rules")
@Tag(name = "Policy Rules Endpoints")
public class PolicyRuleController {

    private final PolicyRuleService service;

    public PolicyRuleController(PolicyRuleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create new policy rule")
    public ResponseEntity<PolicyRuleDto> create(@RequestBody PolicyRuleDto dto) {
        PolicyRule entity = mapToEntity(dto);
        return ResponseEntity.ok(mapToDto(service.createRule(entity)));
    }

    @GetMapping
    @Operation(summary = "List all policy rules")
    public ResponseEntity<List<PolicyRuleDto>> getAll() {
        return ResponseEntity.ok(service.getAllRules().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/active")
    @Operation(summary = "List only active policy rules")
    public ResponseEntity<List<PolicyRuleDto>> getActive() {
        return ResponseEntity.ok(service.getActiveRules().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/active")
    @Operation(summary = "Update policy rule active status")
    public ResponseEntity<Void> updateActive(@PathVariable Long id, @RequestParam boolean active) {
        service.updateRuleActiveStatus(id, active);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete policy rule (soft delete)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.updateRuleActiveStatus(id, false);
        return ResponseEntity.noContent().build();
    }

    private PolicyRuleDto mapToDto(PolicyRule entity) {
        PolicyRuleDto dto = new PolicyRuleDto();
        dto.setId(entity.getId());
        dto.setRuleCode(entity.getRuleCode());
        dto.setDescription(entity.getDescription());
        dto.setAppliesToRole(entity.getAppliesToRole());
        dto.setAppliesToDepartment(entity.getAppliesToDepartment());
        dto.setMaxDevicesAllowed(entity.getMaxDevicesAllowed());
        dto.setActive(entity.getActive());
        return dto;
    }

    private PolicyRule mapToEntity(PolicyRuleDto dto) {
        PolicyRule entity = new PolicyRule();
        entity.setRuleCode(dto.getRuleCode());
        entity.setDescription(dto.getDescription());
        entity.setAppliesToRole(dto.getAppliesToRole());
        entity.setAppliesToDepartment(dto.getAppliesToDepartment());
        entity.setMaxDevicesAllowed(dto.getMaxDevicesAllowed());
        entity.setActive(dto.getActive() == null ? true : dto.getActive());
        return entity;
    }
}