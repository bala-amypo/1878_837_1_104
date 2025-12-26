package com.example.demo.controller;

import com.example.demo.model.EmployeeProfile;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    private final Map<Long, EmployeeProfile> store = new HashMap<>();
    private long idCounter = 1;

    @PostMapping
    public EmployeeProfile create(@RequestBody EmployeeProfile emp) {
        emp.setId(idCounter++);
        store.put(emp.getId(), emp);
        return emp;
    }

    @GetMapping
    public Collection<EmployeeProfile> getAll() {
        return store.values();
    }

    @GetMapping("/{id}")
    public EmployeeProfile getById(@PathVariable Long id) {
        return store.get(id);
    }

    @PutMapping("/{id}")
    public EmployeeProfile update(@PathVariable Long id,
                                  @RequestBody EmployeeProfile emp) {
        emp.setId(id);
        store.put(id, emp);
        return emp;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        store.remove(id);
        return "Employee deleted";
    }
}
