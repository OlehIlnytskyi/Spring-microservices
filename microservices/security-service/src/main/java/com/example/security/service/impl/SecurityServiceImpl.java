package com.example.security.service.impl;

import com.example.security.dto.UserRequest;
import com.example.security.jwt.JwtUtils;
import com.example.security.models.User;
import com.example.security.repository.UserRepository;
import com.example.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<String> register(UserRequest userRequest) {
        User user = dtoToModel(userRequest);

        userRepository.save(user);

        return getToken(userRequest);
    }

    @Override
    public ResponseEntity<String> getToken(UserRequest userRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));

        if (!authenticate.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(JwtUtils.generateToken(userRequest.getUsername()));
    }

    @Override
    public ResponseEntity<Void> checkToken(String token) {
        if (token == null || !token.startsWith("Bearer ")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            JwtUtils.validateToken(token.substring(7));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    private User dtoToModel(UserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
    }
}
