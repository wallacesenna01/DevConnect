package com.br.wallaceartur.DevConnect.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@WebFilter("/*")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extracToken(request);

        logger.debug("Token extraído: {}", token);

        if (token != null) {
            try {
                // Decodifica o token e obtém o username
                DecodedJWT decodedJWT = JWT.decode(token);
                String username = decodedJWT.getSubject();

                // Se o username for válido e o token também for válido, cria a autenticação
                if (username != null && jwtUtil.validateToken(token, username)) {
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
                } else {
                    // Token inválido ou usuário não válido, interrompe a execução
                    logger.error("Token inválido ou expirado.");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido ou expirado.");
                    return; // Interrompe a execução do filtro
                }
            } catch (Exception e) {
                logger.error("Erro ao verificar o token: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado.");
                return; // Interrompe a execução do filtro
            }
        } else {
            // Se não houver token, retorna status 401 (não autorizado)
            logger.error("Token ausente no cabeçalho.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token ausente ou inválido.");
            return;
        }

        // Continua o filtro caso o token seja válido ou o processamento tenha sido interrompido
        filterChain.doFilter(request, response);
    }

    private String extracToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remove o "Bearer " do token
        }
        return null; // Retorna null se o cabeçalho não estiver no formato correto
    }
}

