package com.example.pnc_insurance.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // For simplicity, using hardcoded user. In real app, load from DB
        if ("user".equals(username)) {
            return User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("password")) // Encode the password
                    .roles("USER")
                    .build();
        } else if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin")) // Encode the password
                    .roles("ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
