package com.example.demo.controller;

import com.example.demo.dto.IssuedDeviceDto;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/issued-devices")
@Tag(name = "Issued Device Records Endpoints")
public class IssuedDeviceRecordController {

    private final IssuedDeviceRecordService service;

    public IssuedDeviceRecordController(IssuedDeviceRecordService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Issue a device to employee")
    public ResponseEntity<IssuedDeviceDto> issue(@RequestBody IssuedDeviceDto dto) {
        IssuedDeviceRecord record = new IssuedDeviceRecord();
        record.setEmployee(new EmployeeProfile());
        record.getEmployee().setId(dto.getEmployeeId());
        record.setDeviceItem(new DeviceCatalogItem());
        record.getDeviceItem().setId(dto.getDeviceItemId());

        IssuedDeviceRecord saved = service.issueDevice(record);
        return ResponseEntity.ok(mapToDto(saved));
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return a device")
    public ResponseEntity<IssuedDeviceDto> returnDevice(@PathVariable Long id) {
        return ResponseEntity.ok(mapToDto(service.returnDevice(id)));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get all issued devices for an employee")
    public ResponseEntity<List<IssuedDeviceDto>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getIssuedDevicesByEmployee(employeeId).stream()
                .map(this::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get specific issued device record")
    public ResponseEntity<IssuedDeviceDto> getById(@PathVariable Long id) {
        // Reuse return logic to fetch
        return ResponseEntity.ok(mapToDto(service.returnDevice(id))); // will throw if already returned
    }

    private IssuedDeviceDto mapToDto(IssuedDeviceRecord entity) {
        IssuedDeviceDto dto = new IssuedDeviceDto();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setDeviceItemId(entity.getDeviceItem().getId());
        dto.setIssuedDate(entity.getIssuedDate());
        dto.setReturnedDate(entity.getReturnedDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}