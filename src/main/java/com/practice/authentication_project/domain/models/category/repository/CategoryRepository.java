package com.practice.authentication_project.domain.models.category.repository;

import com.practice.authentication_project.domain.models.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
