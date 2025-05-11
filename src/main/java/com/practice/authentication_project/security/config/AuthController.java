package com.practice.authentication_project.security.config;

import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.domain.models.user.service.UserService;
import com.practice.authentication_project.shared.dto.LoginDTO;
import com.practice.authentication_project.shared.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO register) {

        if(userService.existsByEmail(register.email())) {
            return ResponseEntity.badRequest().body("Username is alredy taken");
        }

        userService.createUser(register);

        return ResponseEntity.ok().body("Register success");

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok().body("Login successfully");

    }

}
