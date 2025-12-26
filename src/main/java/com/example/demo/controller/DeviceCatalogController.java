package com.example.demo.controller;

import com.example.demo.model.DeviceCatalogItem;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceCatalogController {

    private final Map<Long, DeviceCatalogItem> store = new HashMap<>();
    private long id = 1;

    @PostMapping
    public DeviceCatalogItem create(@RequestBody DeviceCatalogItem d) {
        d.setId(id++);
        store.put(d.getId(), d);
        return d;
    }

    @GetMapping
    public Collection<DeviceCatalogItem> getAll() {
        return store.values();
    }

    @PutMapping("/{id}")
    public DeviceCatalogItem update(@PathVariable Long id, @RequestBody DeviceCatalogItem d) {
        d.setId(id);
        store.put(id, d);
        return d;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        store.remove(id);
        return "Deleted";
    }
}
