package com.br.wallaceartur.DevConnect.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@WebFilter("/*")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extracToken(request);
        if(token != null) {

            // decodifica o token e obtem o username
            DecodedJWT decodedJWT = JWT.decode(token);
            String username = decodedJWT.getSubject();

            // cria autenticao do usuario
            if(username != null && jwtUtil.validateToken(token, username)) {
                SecurityContextHolder.
                        getContext().
                        setAuthentication(new UsernamePasswordAuthenticationToken(username,null, new ArrayList<>()));
            }
        }
    }


    private String extracToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // remove o Bearer do token
        }
        return null;
    }
}
