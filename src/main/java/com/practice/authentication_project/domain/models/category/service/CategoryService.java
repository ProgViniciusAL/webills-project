package com.practice.authentication_project.domain.models.category.service;

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
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Transactional
    public Category createCategory(Category category, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for category creation: " + tenantId));
        category.setTenant(tenant);
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(categoryDetails.getName());
        existingCategory.setType(categoryDetails.getType());
        // Alterar o tenant de uma categoria é possível, mas requer atenção
        if (categoryDetails.getTenant() != null && categoryDetails.getTenant().getId() != null) {
            Tenant newTenant = tenantRepository.findById(categoryDetails.getTenant().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("New Tenant not found for category update: " + categoryDetails.getTenant().getId()));
            existingCategory.setTenant(newTenant);
        }
        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
