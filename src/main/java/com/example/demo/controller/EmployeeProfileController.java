package com.example.demo.controller;

import com.example.demo.model.EmployeeProfile;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    private final Map<Long, EmployeeProfile> store = new HashMap<>();
    private long id = 1;

    @PostMapping
    public EmployeeProfile create(@RequestBody EmployeeProfile e) {
        e.setId(id++);
        store.put(e.getId(), e);
        return e;
    }

    @GetMapping
    public Collection<EmployeeProfile> getAll() {
        return store.values();
    }

    @GetMapping("/{id}")
    public EmployeeProfile get(@PathVariable Long id) {
        return store.get(id);
    }

    @PutMapping("/{id}")
    public EmployeeProfile update(@PathVariable Long id, @RequestBody EmployeeProfile e) {
        e.setId(id);
        store.put(id, e);
        return e;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        store.remove(id);
        return "Deleted";
    }
}
