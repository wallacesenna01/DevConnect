package com.br.wallaceartur.DevConnect.controller;

import com.br.wallaceartur.DevConnect.dtos.JwtResponse;
import com.br.wallaceartur.DevConnect.dtos.LoginRequest;
import com.br.wallaceartur.DevConnect.dtos.RegisterRequest;
import com.br.wallaceartur.DevConnect.security.JwtUtil;
import com.br.wallaceartur.DevConnect.services.UserService;
import com.br.wallaceartur.DevConnect.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtil.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        User newUser = userService.registerNewUser(registerRequest);
        return ResponseEntity.status(201).body(newUser);
    }
}
