package org.example.figma.service;

import org.example.figma.entity.Category;
import org.example.figma.model.dto.response.CategoryResDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<List<CategoryResDto>> getCategories();
    List<Category> findAll();
}
