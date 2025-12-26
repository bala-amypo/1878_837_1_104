package com.example.demo.controller;

import com.example.demo.model.IssuedDeviceRecord;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/issued-devices")
public class IssuedDeviceRecordController {

    private final Map<Long, IssuedDeviceRecord> store = new HashMap<>();
    private long idCounter = 1;

    @PostMapping
    public IssuedDeviceRecord issue(@RequestBody IssuedDeviceRecord record) {
        record.setId(idCounter++);
        record.setStatus("ISSUED");
        store.put(record.getId(), record);
        return record;
    }

    @GetMapping
    public Collection<IssuedDeviceRecord> getAll() {
        return store.values();
    }

    @PutMapping("/{id}/return")
    public IssuedDeviceRecord returnDevice(@PathVariable Long id) {
        IssuedDeviceRecord record = store.get(id);
        if (record != null) {
            record.setStatus("RETURNED");
        }
        return record;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        store.remove(id);
        return "Issued record deleted";
    }
}
