package com.br.wallaceartur.DevConnect.controller;

import com.br.wallaceartur.DevConnect.model.UserCredential;
import com.br.wallaceartur.DevConnect.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserCredential userCredential) {
        String username = userCredential.getUsername();
        String token = jwtUtil.generedToken(username);

        System.out.println("Token gerado" + token);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    // Endpoint para validar o token
    @GetMapping("/validate")
    public boolean validateToken(@RequestHeader("Authorization") String token, @RequestParam String username) {
        return jwtUtil.validateToken(token,username);
    }
}
