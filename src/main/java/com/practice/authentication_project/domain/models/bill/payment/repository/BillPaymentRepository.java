package com.practice.authentication_project.domain.models.bill.payment.repository;

import com.practice.authentication_project.domain.models.bill.payment.BillPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillPaymentRepository extends JpaRepository<BillPayment, UUID> {
}
