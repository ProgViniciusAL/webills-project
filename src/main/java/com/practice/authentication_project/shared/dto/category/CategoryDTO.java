package com.practice.authentication_project.shared.dto.category;

import com.practice.authentication_project.domain.models.category.Category;

public record CategoryDTO(Number id, String name, String type) {

    public CategoryDTO(Category category) {
        this(category.getId(), category.getName(), category.getType());
    }

}
