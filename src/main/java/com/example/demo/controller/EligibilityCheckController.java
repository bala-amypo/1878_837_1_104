package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityCheckController {

    @PostMapping
    public String check() {
        return "Eligibility checked (demo)";
    }
}
