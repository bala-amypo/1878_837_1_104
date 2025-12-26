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
        store.put(record.getId(), record);
        return record;
    }

    @PutMapping("/{id}/return")
    public IssuedDeviceRecord returnDevice(@PathVariable Long id) {
        IssuedDeviceRecord r = store.get(id);
        if (r != null) r.setStatus("RETURNED");
        return r;
    }
}
