package com.practice.authentication_project.shared.dto.user;

import com.practice.authentication_project.domain.models.user.Role;
import com.practice.authentication_project.domain.models.user.UserEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record UserResponseDTO(Long id, String name, String email, Role role, OffsetDateTime createdAt) {

    public UserResponseDTO(UserEntity user) {
        this(user.getId() ,user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }

}
