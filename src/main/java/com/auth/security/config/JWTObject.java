package com.auth.security.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTObject {

    private final String SECRET_KEY = System.getenv().get("JWT_SECRET_KEY");
    private final String ISSUER = System.getenv().get("JWT_ISSUER");

    public JWTObject() {
    }

    public String getSecret_key() {
        return SECRET_KEY;
    }


    public String getIssuer() {
        return ISSUER;
    }

}
