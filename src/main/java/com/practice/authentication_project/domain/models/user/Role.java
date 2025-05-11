package com.practice.authentication_project.domain.models.user;

public enum Role {

    ADMIN("ADMIN"),
    USER("USER"),
    MANAGER("MANAGER");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public boolean isUser() {
        return this == USER;
    }

    public boolean isManager() {
        return this == MANAGER;
    }

}
