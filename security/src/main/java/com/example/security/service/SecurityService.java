package com.example.security.service;

import com.example.security.dto.UserRequest;
import org.springframework.http.ResponseEntity;

public interface SecurityService {
    ResponseEntity<String> register(UserRequest userRequest);

    ResponseEntity<String> getToken(UserRequest userRequest);

    ResponseEntity<Void> checkToken(String token);
}
