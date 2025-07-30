package com.em.authservice.service;


import com.em.authservice.dto.LoginRequestDTO;
import com.em.authservice.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;


    public AuthService(UserService userService) {
        this.userService = userService;
    }
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO){
      Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
              .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
              .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));

      return token;


    };
}
