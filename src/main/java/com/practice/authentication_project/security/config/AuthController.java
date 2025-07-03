package com.practice.authentication_project.security.config;

import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.domain.models.user.service.UserService;
import com.practice.authentication_project.security.config.jwt.CookieService;
import com.practice.authentication_project.security.config.jwt.JWTService;
import com.practice.authentication_project.shared.dto.auth.LoginDTO;
import com.practice.authentication_project.shared.dto.auth.LoginResponseDTO;
import com.practice.authentication_project.shared.dto.auth.RegisterDTO;
import com.practice.authentication_project.shared.dto.auth.RegisterResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterDTO register) {

        if(userService.existsByEmail(register.email())) {
            return ResponseEntity.badRequest().body(new RegisterResponseDTO(null, "Username is alredy taken"));
        }

        UserEntity user = userService.createUser(register);

        return ResponseEntity.ok().body(new RegisterResponseDTO(user.getEmail(), "Register sucessfully"));

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO, HttpServletResponse response) {

        log.info("Limpando cookies de usuário: {}", loginDTO.email());
        cookieService.clear("token", response);

        log.info("Autenticando usuário: {}", loginDTO.email());
        var usernamePasswordToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePasswordToken);

        var token = jwtService.generateToken((UserEntity) auth.getPrincipal());

        cookieService.create("token", token, response);

        return ResponseEntity.ok().body(new LoginResponseDTO(token, "Login sucessfully"));

    }

}
