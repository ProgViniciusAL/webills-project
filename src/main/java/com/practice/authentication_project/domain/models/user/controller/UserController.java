package com.practice.authentication_project.domain.models.user.controller;

import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.domain.models.user.service.UserService;
import com.practice.authentication_project.security.config.jwt.JWTService;
import com.practice.authentication_project.shared.dto.user.UserResponseDTO;
import com.practice.authentication_project.shared.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private JWTService jwtService;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUser(HttpServletRequest request) {
        UserEntity user = userService.getAuthenticatedUser(request);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        return ResponseEntity.ok(userService.deleteUser(request));
    }

}
