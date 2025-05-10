package com.practice.authentication_project.domain.models.bill.repository;

import com.practice.authentication_project.domain.models.bill.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, UUID> {
}
