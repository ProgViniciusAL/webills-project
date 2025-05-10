package com.practice.authentication_project.domain.models.account.repository;

import com.practice.authentication_project.domain.models.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
