package com.practice.authentication_project.domain.models.account;

import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.transaction.Transaction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "balance", precision = 10, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(name = "type", length = 255, nullable = false)
    private String type; // Considere usar um Enum: CHECKING, SAVINGS, CREDIT_CARD, etc.

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();
}
