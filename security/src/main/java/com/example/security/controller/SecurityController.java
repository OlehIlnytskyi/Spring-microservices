package com.example.security.controller;

import com.example.security.dto.UserRequest;
import com.example.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/register")
    public String register(@RequestBody UserRequest userRequest) {
        return securityService.register(userRequest);
    }

    @GetMapping("/getToken")
    public String getToken(@RequestBody UserRequest userRequest) {
        return securityService.getToken(userRequest);
    }

    @GetMapping("/checkToken")
    public void checkToken(@RequestHeader("Authorization") String token) {
        securityService.checkToken(token);
    }
}
