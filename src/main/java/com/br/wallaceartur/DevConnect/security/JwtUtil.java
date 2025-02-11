package com.br.wallaceartur.DevConnect.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "CHAVE_SUPER_SEGURA_PARA_JWT_123456";
    private static final long EXPIRATION_TIME_ = 86400000;


    // Gera o token JWT
    public String generedToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);// Usando HMAC-SHA256
        return JWT.create()
                .withSubject(username) // Adiciona o nome de usuário ao token
                .withIssuedAt(new Date()) // adiciona a data de emissao
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_))
                .sign(algorithm); // assina com o algoritmo
    }

    // Extrai o nome de usário ao token
    public String extractUsername(String token) {
        return JWT.decode(token).getSubject(); // o subject do token é o username
    }

    public boolean validateToken(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build(); // Define o algoritmo para verificacao

            DecodedJWT decodedJWT = verifier.verify(token); // verifica o token
            return username.equals(decodedJWT.getSubject()) && !isTokenExpired(decodedJWT);
        }
        catch (JWTVerificationException e) {
            return  false; // se a verificação falhar, o token ão é válido
        }
    }

    // verifica se o token expirou
    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        return  decodedJWT.getExpiresAt().before(new Date());
    }
}
