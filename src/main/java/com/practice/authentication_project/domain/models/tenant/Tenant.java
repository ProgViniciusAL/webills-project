package com.practice.authentication_project.domain.models.tenant;

import com.practice.authentication_project.domain.models.account.Account;
import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.bill.payment.BillPayment;
import com.practice.authentication_project.domain.models.category.Category;
import com.practice.authentication_project.domain.models.notification.Notification;
import com.practice.authentication_project.domain.models.transaction.Transaction;
import com.practice.authentication_project.domain.models.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.access.AccessDeniedException;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tenant")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserEntity> users = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Bill> bills = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BillPayment> billPayments = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Notification> notifications = new ArrayList<>();

    public Boolean validateBillOwnership(Bill bill) {
        if (!bill.getTenant().getId().equals(this.getId())) {
            throw new AccessDeniedException("You are not authorized to update this bill.");
        }

        return true;
    }

    public void validateCategoryOwnership(Category category) {
        if(category.getTenant().getId() != this.getId()) {
            throw new AccessDeniedException("You are not authorized to update this category.");
        }
    }

}
