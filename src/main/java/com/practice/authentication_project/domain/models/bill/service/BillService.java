package com.practice.authentication_project.domain.models.bill.service;

import com.practice.authentication_project.domain.models.bill.Bill;
import com.practice.authentication_project.domain.models.bill.repository.BillRepository;
import com.practice.authentication_project.domain.models.category.Category;
import com.practice.authentication_project.domain.models.category.repository.CategoryRepository;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.domain.models.tenant.repository.TenantRepository;
import com.practice.authentication_project.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Bill createBill(Bill bill, UUID tenantId, Long categoryId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        }

        bill.setTenant(tenant);
        bill.setCategory(category); // Pode ser nulo se não obrigatório
        return billRepository.save(bill);
    }

    @Transactional(readOnly = true)
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Bill getBillById(UUID id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id: " + id));
    }

    @Transactional
    public Bill updateBill(UUID id, Bill billDetails, UUID newTenantId, Long newCategoryId) {
        Bill existingBill = getBillById(id);
        existingBill.setName(billDetails.getName());
        existingBill.setAmount(billDetails.getAmount());
        existingBill.setDueDate(billDetails.getDueDate());
        existingBill.setRecurrence(billDetails.getRecurrence());
        existingBill.setDescription(billDetails.getDescription());

        if (newTenantId != null && !newTenantId.equals(existingBill.getTenant().getId())) {
            Tenant newTenant = tenantRepository.findById(newTenantId)
                    .orElseThrow(() -> new ResourceNotFoundException("New Tenant not found with id: " + newTenantId));
            existingBill.setTenant(newTenant);
        }
        if (newCategoryId != null && (existingBill.getCategory() == null || !newCategoryId.equals(existingBill.getCategory().getId()))) {
            Category newCategory = categoryRepository.findById(newCategoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("New Category not found with id: " + newCategoryId));
            existingBill.setCategory(newCategory);
        } else if (newCategoryId == null && existingBill.getCategory() != null) {
            existingBill.setCategory(null); // Permitir remover categoria
        }

        return billRepository.save(existingBill);
    }

    @Transactional
    public void deleteBill(UUID id) {
        if (!billRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bill not found with id: " + id);
        }
        billRepository.deleteById(id);
    }
}
