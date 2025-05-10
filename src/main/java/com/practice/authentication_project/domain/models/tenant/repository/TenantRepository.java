package com.practice.authentication_project.domain.models.tenant.repository;

import com.practice.authentication_project.domain.models.tenant.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
}
