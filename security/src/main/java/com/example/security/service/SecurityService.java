package com.example.security.service;

import com.example.security.dto.UserRequest;

public interface SecurityService {
    String register(UserRequest userRequest);

    String getToken(UserRequest userRequest);

    void checkToken(String token);
}
