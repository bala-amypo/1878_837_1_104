package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "Device Catalog Endpoints", description = "Manage device catalog items")
@SecurityRequirement(name = "bearerAuth")
public class DeviceCatalogController {

    private final DeviceCatalogService deviceCatalogService;

    public DeviceCatalogController(DeviceCatalogService deviceCatalogService) {
        this.deviceCatalogService = deviceCatalogService;
    }

    @PostMapping
    @Operation(summary = "Create a new device catalog item")
    public ResponseEntity<DeviceCatalogItem> createDevice(@RequestBody DeviceCatalogItem item) {
        DeviceCatalogItem created = deviceCatalogService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Get all device catalog items")
    public ResponseEntity<List<DeviceCatalogItem>> getAllDevices() {
        List<DeviceCatalogItem> devices = deviceCatalogService.getAllItems();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device catalog item by ID")
    public ResponseEntity<DeviceCatalogItem> getDeviceById(@PathVariable Long id) {
        DeviceCatalogItem device = deviceCatalogService.getItemById(id);
        return ResponseEntity.ok(device);
    }

    @PutMapping("/{id}/active")
    @Operation(summary = "Update device active status")
    public ResponseEntity<DeviceCatalogItem> updateDeviceActiveStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        DeviceCatalogItem updated = deviceCatalogService.updateActiveStatus(id, active);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete device catalog item")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceCatalogService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
