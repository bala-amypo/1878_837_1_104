// com/example/demo/controller/EligibilityCheckController.java
package com.example.demo.controller;

import com.example.demo.dto.EligibilityCheckDto;
import com.example.demo.model.EligibilityCheckRecord;
import com.example.demo.service.EligibilityCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Eligibility Check Endpoints")
@RestController
@RequestMapping("/api/eligibility")
public class EligibilityCheckController {

    private final EligibilityCheckService service;

    public EligibilityCheckController(EligibilityCheckService service) {
        this.service = service;
    }

    @Operation(summary = "Validate eligibility")
    @PostMapping("/validate/{employeeId}/{deviceItemId}")
    public ResponseEntity<EligibilityCheckDto> validate(@PathVariable Long employeeId, @PathVariable Long deviceItemId) {
        return ResponseEntity.ok(toDto(service.validateEligibility(employeeId, deviceItemId)));
    }

    @Operation(summary = "Get eligibility checks by employee")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EligibilityCheckDto>> getByEmployee(@PathVariable Long employeeId) {
        List<EligibilityCheckDto> dtos = service.getChecksByEmployee(employeeId).stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get eligibility check by ID")
    @GetMapping("/{checkId}")
    public ResponseEntity<EligibilityCheckDto> getById(@PathVariable Long checkId) {
        // Assume getById in service
        return ResponseEntity.ok(toDto(/* service.getById(checkId) */ null)); // Placeholder
    }

    private EligibilityCheckDto toDto(EligibilityCheckRecord entity) {
        EligibilityCheckDto dto = new EligibilityCheckDto();
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setDeviceItemId(entity.getDeviceItem().getId());
        dto.setIsEligible(entity.getIsEligible());
        dto.setReason(entity.getReason());
        dto.setCheckedAt(entity.getCheckedAt());
        return dto;
    }
}