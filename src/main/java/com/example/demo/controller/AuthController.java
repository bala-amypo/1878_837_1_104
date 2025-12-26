package com.example.demo.controller;

import com.example.demo.model.UserAccount;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/token")
    public String generateToken(@RequestBody UserAccount user) {
        return jwtTokenProvider.generateToken(user);
    }
}
