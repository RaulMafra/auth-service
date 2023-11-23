package com.auth.security;

import com.auth.security.config.JWTObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    @Autowired
    private JWTObject jwtObject;

    public String generationToken(UserDetailsImpl user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtObject.getSecret_key());
            return JWT.create()
                    .withIssuer(jwtObject.getIssuer())
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
            Algorithm algorithm = Algorithm.HMAC256(jwtObject.getSecret_key());
            return JWT.require(algorithm)
                    .withIssuer(jwtObject.getIssuer())
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
