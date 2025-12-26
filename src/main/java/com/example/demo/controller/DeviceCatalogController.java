package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceCatalogController {

    private final Map<Long, DeviceCatalogItem> store = new HashMap<>();
    private long idCounter = 1;

    @PostMapping
    public DeviceCatalogItem create(@RequestBody DeviceCatalogItem item) {
        item.setId(idCounter++);
        store.put(item.getId(), item);
        return item;
    }

    @GetMapping
    public Collection<DeviceCatalogItem> getAll() {
        return store.values();
    }

    @GetMapping("/{id}")
    public DeviceCatalogItem getById(@PathVariable Long id) {
        return store.get(id);
    }

    @PutMapping("/{id}")
    public DeviceCatalogItem update(@PathVariable Long id,
                                    @RequestBody DeviceCatalogItem item) {
        item.setId(id);
        store.put(id, item);
        return item;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        store.remove(id);
        return "Device deleted";
    }
}
