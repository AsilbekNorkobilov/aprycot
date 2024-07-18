package org.example.figma.service;

import org.example.figma.entity.Category;
import org.example.figma.model.dto.request.CategoryDto;
import org.example.figma.model.dto.response.CategoryResDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    ResponseEntity<List<CategoryResDto>> getCategories();
    List<Category> findAll();

    UUID saveCategory(String name);

    String saveCategoryPhoto(CategoryDto categoryDto) throws IOException;

    String archiveCategory(UUID categoryId);
}
