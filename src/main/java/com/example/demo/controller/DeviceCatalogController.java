package com.example.demo.controller;

import com.example.demo.dto.DeviceDto;
import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "Device Catalog Endpoints")
public class DeviceCatalogController {

    private final DeviceCatalogService service;

    public DeviceCatalogController(DeviceCatalogService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create new device catalog item")
    public ResponseEntity<DeviceDto> create(@RequestBody DeviceDto dto) {
        DeviceCatalogItem entity = mapToEntity(dto);
        DeviceCatalogItem saved = service.createItem(entity);
        return ResponseEntity.ok(mapToDto(saved));
    }

    @GetMapping
    @Operation(summary = "List all device items")
    public ResponseEntity<List<DeviceDto>> getAll() {
        return ResponseEntity.ok(service.getAllItems().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device item by ID")
    public ResponseEntity<DeviceDto> getById(@PathVariable Long id) {
        DeviceCatalogItem item = service.updateActiveStatus(id, true); // just to fetch
        return ResponseEntity.ok(mapToDto(item));
    }

    @PutMapping("/{id}/active")
    @Operation(summary = "Update device active status")
    public ResponseEntity<DeviceDto> updateActive(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(mapToDto(service.updateActiveStatus(id, active)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete device item (soft delete)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.updateActiveStatus(id, false);
        return ResponseEntity.noContent().build();
    }

    private DeviceDto mapToDto(DeviceCatalogItem entity) {
        DeviceDto dto = new DeviceDto();
        dto.setId(entity.getId());
        dto.setDeviceCode(entity.getDeviceCode());
        dto.setDeviceType(entity.getDeviceType());
        dto.setModel(entity.getModel());
        dto.setMaxAllowedPerEmployee(entity.getMaxAllowedPerEmployee());
        dto.setActive(entity.getActive());
        return dto;
    }

    private DeviceCatalogItem mapToEntity(DeviceDto dto) {
        DeviceCatalogItem entity = new DeviceCatalogItem();
        entity.setDeviceCode(dto.getDeviceCode());
        entity.setDeviceType(dto.getDeviceType());
        entity.setModel(dto.getModel());
        entity.setMaxAllowedPerEmployee(dto.getMaxAllowedPerEmployee());
        entity.setActive(dto.getActive() == null ? true : dto.getActive());
        return entity;
    }
}