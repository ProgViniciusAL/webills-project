package com.practice.authentication_project.domain.models.transaction.repository;

import com.practice.authentication_project.domain.models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.function.LongFunction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
