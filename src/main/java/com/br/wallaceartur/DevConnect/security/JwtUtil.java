package com.br.wallaceartur.DevConnect.security;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "CHAVE_SUPER_SEGURA_PARA_JWT_123456";
    private static final long EXPIRATION_TIME_ = 86400000;
}
