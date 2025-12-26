package com.example.demo.controller;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issued-devices")
@Tag(name = "Issued Device Records Endpoints", description = "Manage device issuance and returns")
@SecurityRequirement(name = "bearerAuth")
public class IssuedDeviceRecordController {

    private final IssuedDeviceRecordService issuedDeviceRecordService;

    public IssuedDeviceRecordController(IssuedDeviceRecordService issuedDeviceRecordService) {
        this.issuedDeviceRecordService = issuedDeviceRecordService;
    }

    @PostMapping
    @Operation(summary = "Issue a device to an employee")
    public ResponseEntity<IssuedDeviceRecord> issueDevice(@RequestBody IssuedDeviceRecord record) {
        IssuedDeviceRecord issued = issuedDeviceRecordService.issueDevice(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(issued);
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Mark a device as returned")
    public ResponseEntity<IssuedDeviceRecord> returnDevice(@PathVariable Long id) {
        IssuedDeviceRecord returned = issuedDeviceRecordService.returnDevice(id);
        return ResponseEntity.ok(returned);
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get all issued devices for an employee")
    public ResponseEntity<List<IssuedDeviceRecord>> getIssuedDevicesByEmployee(@PathVariable Long employeeId) {
        List<IssuedDeviceRecord> records = issuedDeviceRecordService.getIssuedDevicesByEmployee(employeeId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issued device record by ID")
    public ResponseEntity<IssuedDeviceRecord> getRecordById(@PathVariable Long id) {
        IssuedDeviceRecord record = issuedDeviceRecordService.getRecordById(id);
        return ResponseEntity.ok(record);
    }
}

