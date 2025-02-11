package com.br.wallaceartur.DevConnect.controller;

import com.br.wallaceartur.DevConnect.model.UserCredencials;
import com.br.wallaceartur.DevConnect.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint para login
    @PostMapping("/login")
    public String login(@RequestBody UserCredencials userCredencials) {
        String username = userCredencials.getUsername();
        String token = jwtUtil.generedToken(username);
        return token;
    }

    // Endpoint para validar o token
    @GetMapping("/validate")
    public boolean validateToken(@RequestHeader("Authorization") String token, @RequestParam String username) {
        return jwtUtil.validateToken(token,username);
    }
}
