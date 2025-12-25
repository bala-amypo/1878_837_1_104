// File: src/main/java/com/example/demo/controller/DeviceCatalogController.java
package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DeviceCatalog Endpoints")
@RestController
@RequestMapping("/api/devices")
public class DeviceCatalogController {

    private final DeviceCatalogService service;

    public DeviceCatalogController(DeviceCatalogService service) {
        this.service = service;
    }

    @Operation(summary = "Create new device catalog item")
    @PostMapping
    public ResponseEntity<DeviceCatalogItem> create(@RequestBody DeviceCatalogItem item) {
        DeviceCatalogItem created = service.createItem(item);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "List all device catalog items")
    @GetMapping
    public ResponseEntity<List<DeviceCatalogItem>> getAll() {
        List<DeviceCatalogItem> items = service.getAllItems();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Get specific device catalog item")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceCatalogItem> getById(@PathVariable Long id) {
        // Not in service interface per spec — can be added later
        throw new UnsupportedOperationException("Get by ID not implemented");
    }

    @Operation(summary = "Update device active status")
    @PutMapping("/{id}/active")
    public ResponseEntity<DeviceCatalogItem> updateActive(@PathVariable Long id, @RequestParam boolean active) {
        DeviceCatalogItem updated = service.updateActiveStatus(id, active);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete device catalog item")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Delete not in service interface per spec
        throw new UnsupportedOperationException("Delete not implemented");
    }
}