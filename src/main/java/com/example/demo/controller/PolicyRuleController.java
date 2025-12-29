package com.example.demo.controller;

import com.example.demo.model.PolicyRule;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/policies")
public class PolicyRuleController {

    private final Map<Long, PolicyRule> store = new HashMap<>();
    private long id = 1;

    @PostMapping
    public PolicyRule create(@RequestBody PolicyRule p) {
        p.setId(id++);
        store.put(p.getId(), p);
        return p;
    }

    @GetMapping
    public Collection<PolicyRule> getAll() {
        return store.values();
    }
}
