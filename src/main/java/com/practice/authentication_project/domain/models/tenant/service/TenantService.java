package com.practice.authentication_project.domain.models.tenant.service;

import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.repository.TenantRepository;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Transactional
    public Tenant createTenant(Tenant tenant) {
        // Validações ou lógica de negócio antes de salvar podem ser adicionadas aqui
        return tenantRepository.save(tenant);
    }

    @Transactional(readOnly = true)
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tenant getTenantById(UUID id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + id));
    }

    @Transactional
    public Tenant updateTenant(UUID id, Tenant tenantDetails) {
        Tenant existingTenant = getTenantById(id); // Reusa o metodo que já lança exceção se não encontrar
        existingTenant.setName(tenantDetails.getName());
        // Outros campos que podem ser atualizados
        // Atenção ao atualizar coleções e relacionamentos, pode exigir lógica mais complexa
        return tenantRepository.save(existingTenant);
    }

    @Transactional
    public void deleteTenant(UUID id) {
        if (!tenantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tenant not found with id: " + id);
        }
        tenantRepository.deleteById(id);
    }
}