package com.practice.authentication_project.domain.models.bill;

import com.practice.authentication_project.domain.models.bill.payment.BillPayment;
import com.practice.authentication_project.domain.models.category.Category;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.transaction.Transaction;
import com.practice.authentication_project.shared.dto.bills.BillsDTO;
import com.practice.authentication_project.shared.dto.bills.BillsUpdateDTO;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "recurrence", length = 255)
    private String recurrence;

    @Column(name = "bill_status", nullable = false)
    private Status status;

    @Column(name = "description", length = 255)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id") // Pode ser nulo se uma conta não tiver categoria obrigatória
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BillPayment> billPayments = new ArrayList<>();

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY) // Transações associadas a esta conta
    private List<Transaction> transactions = new ArrayList<>();

    public void updateFrom(BillsUpdateDTO updatedBill, Category newCategory) {
        this.setName(updatedBill.name());
        this.setAmount(updatedBill.amount());
        this.setDueDate(updatedBill.dueDate());
        this.setRecurrence(updatedBill.recurrence());
        this.setDescription(updatedBill.description());

        if (!newCategory.getTenant().getId().equals(tenant.getId())) {
            throw new AccessDeniedException("You are not authorized to use this category.");
        }

        this.setCategory(updatedBill.categoryId() != null ? newCategory : null);

    }

    public Bill(BillsDTO updatedBill, Category newCategory, Tenant tenant) {

        this.name = updatedBill.name();
        this.amount = updatedBill.amount();
        this.dueDate = updatedBill.dueDate();
        this.status = Status.valueOf(updatedBill.status());
        this.recurrence = updatedBill.recurrence();
        this.description = updatedBill.description();
        this.tenant = tenant;

        if (!newCategory.getTenant().getId().equals(tenant.getId())) {
            throw new AccessDeniedException("You are not authorized to use this category.");
        }
        this.setCategory(updatedBill.categoryId() != null ? newCategory : null);

    }
}
