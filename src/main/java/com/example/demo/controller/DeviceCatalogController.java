package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import com.example.demo.service.DeviceCatalogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceCatalogController {

    private final DeviceCatalogService service;

    public DeviceCatalogController(DeviceCatalogService service) {
        this.service = service;
    }

    @PostMapping
    public DeviceCatalogItem create(@RequestBody DeviceCatalogItem item) {
        return service.create(item);
    }

    @GetMapping("/{id}")
    public DeviceCatalogItem getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<DeviceCatalogItem> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public DeviceCatalogItem update(@PathVariable Long id,
                                    @RequestBody DeviceCatalogItem item) {
        return service.update(id, item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
