package com.practice.authentication_project.domain.models.transaction;

import com.practice.authentication_project.domain.models.account.Account;
import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.category.Category;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // Pode ser nulo
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id") // Pode ser nulo, se a transação não estiver diretamente ligada a uma conta específica
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
}
