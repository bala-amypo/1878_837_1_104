// com/example/demo/controller/IssuedDeviceRecordController.java
package com.example.demo.controller;

import com.example.demo.dto.IssuedDeviceDto;
import com.example.demo.model.IssuedDeviceRecord;
import com.example.demo.service.IssuedDeviceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Issued Device Records Endpoints")
@RestController
@RequestMapping("/api/issued-devices")
public class IssuedDeviceRecordController {

    private final IssuedDeviceRecordService service;

    public IssuedDeviceRecordController(IssuedDeviceRecordService service) {
        this.service = service;
    }

    @Operation(summary = "Issue device")
    @PostMapping
    public ResponseEntity<IssuedDeviceDto> issue(@RequestBody IssuedDeviceDto dto) {
        IssuedDeviceRecord record = toEntity(dto);
        IssuedDeviceRecord saved = service.issueDevice(record);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @Operation(summary = "Return device")
    @PutMapping("/{id}/return")
    public ResponseEntity<IssuedDeviceDto> returnDevice(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(service.returnDevice(id)));
    }

    @Operation(summary = "Get issued devices by employee")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<IssuedDeviceDto>> getByEmployee(@PathVariable Long employeeId) {
        List<IssuedDeviceDto> dtos = service.getIssuedDevicesByEmployee(employeeId).stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get issued device by ID")
    @GetMapping("/{id}")
    public ResponseEntity<IssuedDeviceDto> getById(@PathVariable Long id) {
        // Assume getById in service
        return ResponseEntity.ok(toDto(/* service.getById(id) */ null)); // Placeholder
    }

    private IssuedDeviceDto toDto(IssuedDeviceRecord entity) {
        IssuedDeviceDto dto = new IssuedDeviceDto();
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setDeviceItemId(entity.getDeviceItem().getId());
        dto.setIssuedDate(entity.getIssuedDate());
        dto.setReturnedDate(entity.getReturnedDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private IssuedDeviceRecord toEntity(IssuedDeviceDto dto) {
        IssuedDeviceRecord record = new IssuedDeviceRecord();
        // Set employee and device from IDs, assume fetched in service
        return record;
    }
}