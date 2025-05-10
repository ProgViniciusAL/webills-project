package com.practice.authentication_project.domain.models.user;

import com.practice.authentication_project.domain.models.notification.Notification;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime createdAt;

    // Relacionamento Many-to-Many com Tenant
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_tenants",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id")
    )
    private Set<Tenant> tenants = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Notification> notifications = new ArrayList<>();
}
