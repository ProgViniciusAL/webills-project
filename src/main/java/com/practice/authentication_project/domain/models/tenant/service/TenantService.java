package com.practice.authentication_project.domain.models.tenant.service;

import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.repository.TenantRepository;
import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.domain.models.user.repository.UserRepository;
import com.practice.authentication_project.security.config.jwt.JWTService;
import com.practice.authentication_project.shared.dto.tenant.TenantCreateDTO;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Tenant createTenant(TenantCreateDTO tenantDetails, HttpServletRequest request) {

        String token = jwtService.recoverToken(request);
        String subject = jwtService.validadeToken(token);

        UserEntity authenticatedUser = userRepository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(authenticatedUser.getTenant() == null) {
            Tenant newTenant = new Tenant();
            newTenant.setName(tenantDetails.name());
            authenticatedUser.setTenant(newTenant);
            return tenantRepository.save(newTenant);
        }

        return null;
    }

    @Transactional(readOnly = true)
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tenant getTenantById(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new ResourceNotFoundException("Token not found in cookies");
        }

        String token = jwtService.recoverToken(request);
        String subject = jwtService.validadeToken(token);

        UserEntity user = userRepository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Tenant userTenant = user.getTenant();

        return tenantRepository.findById(userTenant.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + userTenant.getId()));
    }

    @Transactional
    public Tenant updateTenant(UUID id, HttpServletRequest request) {
        // Reusa o metodo que já lança exceção se não encontrar
        // Outros campos que podem ser atualizados
        // Atenção ao atualizar coleções e relacionamentos, pode exigir lógica mais complex
        return null;
    }

    @Transactional
    public void deleteTenant(UUID id) {
        if (!tenantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tenant not found with id: " + id);
        }
        tenantRepository.deleteById(id);
    }
}