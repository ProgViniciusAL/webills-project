package com.practice.authentication_project.shared.dto.bills;

import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.bill.payment.BillPayment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BillPaymentDTO(UUID id, BigDecimal amount, Boolean isFullPayment ,LocalDate paymentDate) {

    public BillPaymentDTO(BillPayment billPayment) {
        this(billPayment.getId(), billPayment.getAmountPaid(), billPayment.isFullPayment(), billPayment.getPaymentDate());
    }

}
