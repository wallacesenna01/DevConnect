package com.br.wallaceartur.DevConnect.controller;

import com.br.wallaceartur.DevConnect.dtos.JwtResponse;
import com.br.wallaceartur.DevConnect.dtos.LoginRequest;
import com.br.wallaceartur.DevConnect.dtos.RegisterRequest;
import com.br.wallaceartur.DevConnect.entities.User;
import com.br.wallaceartur.DevConnect.security.JwtUtil;
import com.br.wallaceartur.DevConnect.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // Recupera o usuário autenticado
        User user = userService.findByUsername(loginRequest.getUsername());

        // Verifica se o usuário foi encontrado
        if (user == null) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(new JwtResponse("Credenciais inválidas"));
        }

        // Recupera as roles do usuário
        List<String> roles = getRolesForUser(user);

        // Gera o token JWT com base no username e nas roles
        String token = jwtUtil.generateToken(user.getUsername(), roles);

        // Retorna o token como resposta
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private List<String> getRolesForUser(User user) {
        // Recupera as roles do usuário do banco de dados
        return user.getRoles().stream()
                .map(role -> role.name())  // Converte a role para String
                .collect(Collectors.toList());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // Registra um novo usuário
        User newUser = userService.registerNewUser(registerRequest);
        return ResponseEntity.status(201).body(newUser);
    }
}
