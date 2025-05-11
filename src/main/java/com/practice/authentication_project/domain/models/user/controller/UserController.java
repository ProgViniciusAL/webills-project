package com.practice.authentication_project.domain.models.user.controller;

import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.domain.models.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {

        List<UserEntity> usersList = userService.getAllUsers();

        return ResponseEntity.ok(usersList);

    }

}
