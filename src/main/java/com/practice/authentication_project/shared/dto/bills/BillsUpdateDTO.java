package com.practice.authentication_project.shared.dto.bills;

import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.category.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BillsUpdateDTO(UUID id, String name, BigDecimal amount, LocalDate dueDate, String recurrence, String description, Long categoryId) {
}
