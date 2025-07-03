package com.practice.authentication_project.domain.models.tenant.controller;

import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.service.TenantService;
import com.practice.authentication_project.security.config.AuthorizationService;
import com.practice.authentication_project.security.config.jwt.JWTService;
import com.practice.authentication_project.shared.dto.bills.BillsDTO;
import com.practice.authentication_project.shared.dto.tenant.TenantCreateDTO;
import com.practice.authentication_project.shared.dto.tenant.TenantResponseDTO;
import com.practice.authentication_project.shared.dto.user.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody TenantCreateDTO tenantDetails, HttpServletRequest request) {

        Tenant tenant = tenantService.createTenant(tenantDetails, request);

        return ResponseEntity.ok().body(tenant);
    }

    @GetMapping
    public ResponseEntity<TenantResponseDTO> getTenantById(HttpServletRequest request) {

        Tenant tenant = tenantService.getTenantById(request);

        return ResponseEntity.ok().body(new TenantResponseDTO(tenant.getName(), tenant.getCreatedAt(), tenant.getUsers().stream().map(UserResponseDTO::new).toList(), tenant.getBills().stream().map(BillsDTO::new).toList()));
    }

}
