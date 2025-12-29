package com.example.demo.controller;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issued-devices")
@Tag(name = "Issued Device Controller", description = "Operations for tracking issued equipment")
public class IssuedDeviceRecordController {
    private final IssuedDeviceRecordService issuedDeviceService;

    public IssuedDeviceRecordController(IssuedDeviceRecordService issuedDeviceService) {
        this.issuedDeviceService = issuedDeviceService;
    }

    @PostMapping
    public ResponseEntity<IssuedDeviceRecord> issueDevice(@RequestBody IssuedDeviceRecord record) {
        return new ResponseEntity<>(issuedDeviceService.issueDevice(record), HttpStatus.CREATED);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<IssuedDeviceRecord>> getRecordsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(issuedDeviceService.getRecordsByEmployee(employeeId));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<IssuedDeviceRecord> returnDevice(@PathVariable Long id) {
        return ResponseEntity.ok(issuedDeviceService.markAsReturned(id));
    }
}