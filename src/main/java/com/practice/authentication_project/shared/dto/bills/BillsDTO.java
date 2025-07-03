package com.practice.authentication_project.shared.dto.bills;

import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.bill.payment.BillPayment;
import com.practice.authentication_project.domain.models.category.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record BillsDTO(

        UUID id,
        String name,
        BigDecimal amount,
        LocalDate dueDate,
        String recurrence,
        String status,
        String description,
        OffsetDateTime createdAt,
        List<BillPaymentDTO> payments,
        Long categoryId
) {

    public BillsDTO(Bill bill) {
        this(
                bill.getId(),
                bill.getName(),
                bill.getAmount(),
                bill.getDueDate(),
                bill.getRecurrence(),
                bill.getStatus() != null ? bill.getStatus().name() : null,
                bill.getDescription(),
                bill.getCreatedAt(),
                bill.getBillPayments().stream().map(BillPaymentDTO::new).toList(),
                bill.getCategory() != null ? bill.getCategory().getId() : null
        );
    }

}
