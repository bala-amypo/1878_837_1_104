package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String home() {
        return "Application is running";
    }

    @GetMapping("/api/health")
    public String health() {
        return "OK";
    }
}
