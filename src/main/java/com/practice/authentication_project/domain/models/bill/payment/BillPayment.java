package com.practice.authentication_project.domain.models.bill.payment;

import com.practice.authentication_project.domain.models.bill.Bill;
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
@Table(name = "bill_payments")
public class BillPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "amount_paid", precision = 10, scale = 2, nullable = false)
    private BigDecimal amountPaid;

    @Column(name = "is_full_payment", nullable = false)
    private boolean isFullPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
}
