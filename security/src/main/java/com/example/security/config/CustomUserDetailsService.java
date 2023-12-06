package com.example.security.config;

import com.example.security.models.User;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user
                .map(this::mapToSec)
                .orElseThrow(() -> new UsernameNotFoundException("User with name \"" + username + "\" was not found"));
    }

    private CustomUserDetails mapToSec(User user) {
        return new CustomUserDetails(user.getUsername(), user.getPassword());
    }
}
