package com.practice.authentication_project.domain.models.category.controller;

import com.practice.authentication_project.domain.models.category.Category;
import com.practice.authentication_project.domain.models.category.service.CategoryService;
import com.practice.authentication_project.domain.models.tenant.Tenant;
import com.practice.authentication_project.shared.dto.category.CategoryDTO;
import com.practice.authentication_project.shared.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SessionUtil sessionUtil;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO category, HttpServletRequest request) {

        UUID tenantId = sessionUtil.getSessionTenant(request).getId();

        Category createdCategory = categoryService.createCategory(category, tenantId);
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories.stream().map(CategoryDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new CategoryDTO(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
