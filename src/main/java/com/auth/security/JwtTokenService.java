package com.auth.security;

import com.auth.security.config.JWTObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    public String generationToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWTObject.JWT_SECRET_KEY);
            return JWT.create()
                    .withIssuer(JWTObject.JWT_ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException(e.getMessage(), e);
        }
    }

    public String verificationToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWTObject.JWT_SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(JWTObject.JWT_ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch(JWTVerificationException e) {
            throw new JWTVerificationException(e.getMessage());
        }
             }


    public Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    public Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(3).toInstant();

    }
}
