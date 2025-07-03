package com.practice.authentication_project.security.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@Service
public class JWTService {

    @Autowired
    private CookieService cookieService;

    @Value("${api.security.secret.key}")
    private String SECRET_KEY;

    public String generateToken(UserEntity user) {

        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.create()
                    .withIssuer("webills-auth-service")
                    .withSubject(user.getEmail())
                    .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")))
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch(JWTCreationException e) {
            throw new JWTCreationException("Erro ao criar o token: ", e);
        }

    }

    public String validadeToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer("webills-auth-service")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch(JWTVerificationException e) {
            throw new JWTVerificationException("Erro ao validar o token", e);
        }

    }

    public String recoverToken(HttpServletRequest request) {

        String cookie = cookieService.recoverCookie(request);
        log.info(cookie);

        return cookie;
    }


    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


}
