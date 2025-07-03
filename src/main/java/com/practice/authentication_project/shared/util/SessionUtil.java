package com.practice.authentication_project.shared.util;

import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.repository.TenantRepository;
import com.practice.authentication_project.domain.models.user.UserEntity;
import com.practice.authentication_project.domain.models.user.repository.UserRepository;
import com.practice.authentication_project.security.config.jwt.JWTService;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private TenantRepository tenantRepository;

    public Tenant getSessionTenant(HttpServletRequest request) {

        String token = jwtService.recoverToken(request);
        String subject = jwtService.validadeToken(token);

        UserEntity user = userRepository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Tenant userTenant = user.getTenant();

        return tenantRepository.findById(userTenant.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + userTenant.getId()));

    }

}
