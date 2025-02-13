package com.br.wallaceartur.DevConnect.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
public class JwtUtil {

    private static final String SECRET_KEY = "CHAVE_SUPER_SEGURA_PARA_JWT_11111";



public String generateToken(String username, List<String> roles ) {
    try {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withExpiresAt(getExpirationDate())
                .sign(algorithm);
                return token;
    }
    catch (JWTVerificationException exception) {
        throw new RuntimeException("Error while genereation token", exception);

    }


}
    private Date getExpirationDate() {
        return Date.from(Date.from(LocalDateTime.now()
                .plusHours(5)
                .toInstant(ZoneOffset.of("-03:00"))).toInstant());
    }


    public boolean validateToken(String token, String username) {
        try {

            if (token == null || token.trim().isEmpty()) {
                return false; // Retorna falso caso o token seja nulo ou vazio
            }

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build(); // Define o algoritmo para verificacao

            DecodedJWT decodedJWT = verifier.verify(token); // verifica o token
            if (!username.equals(decodedJWT.getSubject())){
                return false;
            }

            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            if(roles == null || !roles.contains("ROLE_USER")){
                return false;
            }

            return !isTokenExpired(decodedJWT);
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
