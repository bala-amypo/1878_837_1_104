package com.example.demo.controller;

import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAccountRepository repository;

    public AuthController(UserAccountRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/register")
    public UserAccount register(@RequestBody UserAccount user) {
        return repository.save(user);
    }

    @GetMapping("/login/{username}")
    public Optional<UserAccount> login(@PathVariable String username) {
        return repository.findByUsername(username);
    }
}