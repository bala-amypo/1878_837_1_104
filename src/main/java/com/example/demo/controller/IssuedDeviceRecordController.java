// File: src/main/java/com/example/demo/controller/IssuedDeviceRecordController.java
package com.example.demo.controller;

import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "IssuedDeviceRecordsEndpoints")
@RestController
@RequestMapping("/api/issued-devices")
public class IssuedDeviceRecordController {

    private final IssuedDeviceRecordService service;

    public IssuedDeviceRecordController(IssuedDeviceRecordService service) {
        this.service = service;
    }

    @Operation(summary = "Issue a device to employee")
    @PostMapping
    public ResponseEntity<IssuedDeviceRecord> issueDevice(@RequestBody IssuedDeviceRecord record) {
        IssuedDeviceRecord issued = service.issueDevice(record);
        return ResponseEntity.ok(issued);
    }

    @Operation(summary = "Return a device")
    @PutMapping("/{id}/return")
    public ResponseEntity<IssuedDeviceRecord> returnDevice(@PathVariable Long id) {
        IssuedDeviceRecord returned = service.returnDevice(id);
        return ResponseEntity.ok(returned);
    }

    @Operation(summary = "Get all issued devices for an employee")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<IssuedDeviceRecord>> getByEmployee(@PathVariable Long employeeId) {
        List<IssuedDeviceRecord> devices = service.getIssuedDevicesByEmployee(employeeId);
        return ResponseEntity.ok(devices);
    }

    @Operation(summary = "Get specific issued device record")
    @GetMapping("/{id}")
    public ResponseEntity<IssuedDeviceRecord> getById(@PathVariable Long id) {
        // Not in service interface
        throw new UnsupportedOperationException("Get by ID not implemented");
    }
}