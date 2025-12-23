// com/example/demo/controller/DeviceCatalogController.java
package com.example.demo.controller;

import com.example.demo.dto.DeviceDto;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Device Catalog Endpoints")
@RestController
@RequestMapping("/api/devices")
public class DeviceCatalogController {

    private final DeviceCatalogService service;

    public DeviceCatalogController(DeviceCatalogService service) {
        this.service = service;
    }

    @Operation(summary = "Create device catalog item")
    @PostMapping
    public ResponseEntity<DeviceDto> create(@RequestBody DeviceDto dto) {
        DeviceCatalogItem item = toEntity(dto);
        DeviceCatalogItem saved = service.createItem(item);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all devices")
    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAll() {
        List<DeviceDto> dtos = service.getAllItems().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Get device by ID")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getById(@PathVariable Long id) {
        // Assume getById in service, add if needed
        return ResponseEntity.ok(toDto(/* service.getById(id) */ null)); // Placeholder
    }

    @Operation(summary = "Update device active status")
    @PutMapping("/{id}/active")
    public ResponseEntity<DeviceDto> updateActive(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(toDto(service.updateActiveStatus(id, active)));
    }

    @Operation(summary = "Delete device")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Assume delete in service
        return ResponseEntity.noContent().build();
    }

    private DeviceDto toDto(DeviceCatalogItem entity) {
        DeviceDto dto = new DeviceDto();
        dto.setDeviceCode(entity.getDeviceCode());
        dto.setDeviceType(entity.getDeviceType());
        dto.setModel(entity.getModel());
        dto.setMaxAllowedPerEmployee(entity.getMaxAllowedPerEmployee());
        dto.setActive(entity.getActive());
        return dto;
    }

    private DeviceCatalogItem toEntity(DeviceDto dto) {
        return new DeviceCatalogItem(dto.getDeviceCode(), dto.getDeviceType(), dto.getModel(), dto.getMaxAllowedPerEmployee());
    }
}