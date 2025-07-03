package com.practice.authentication_project.domain.models.category;

import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.transaction.Transaction;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "type", length = 255, nullable = false)
    private String type; // Considere usar um Enum: INCOME, EXPENSE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)// Cascade pode não ser desejável aqui
    private List<Bill> bills = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY) // Cascade pode não ser desejável aqui
    private List<Transaction> transactions = new ArrayList<>();
}
