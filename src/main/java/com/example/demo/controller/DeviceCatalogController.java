package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceCatalogController {

    private final DeviceCatalogService service;

    public DeviceCatalogController(DeviceCatalogService service) {
        this.service = service;
    }

    @PostMapping
    public DeviceCatalogItem create(@RequestBody DeviceCatalogItem item) {
        return service.createItem(item);
    }

    @GetMapping
    public List<DeviceCatalogItem> getAll() {
        return service.getAllItems();
    }

    @PutMapping("/{id}/status")
    public DeviceCatalogItem updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        return service.updateActiveStatus(id, active);
    }
}
