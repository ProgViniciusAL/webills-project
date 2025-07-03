package com.practice.authentication_project.security.config.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class CookieService {

    public void create(String name, String token, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(name, token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofHours(2))
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        log.info("Created cookie: {}", cookie);
    }

    public void clear(String name, HttpServletResponse response) {
        ResponseCookie clearCookie = ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, clearCookie.toString());
    }

    public String recoverCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    log.info("Token: {}", cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


}
