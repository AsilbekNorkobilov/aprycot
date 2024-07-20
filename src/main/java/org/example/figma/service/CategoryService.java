package org.example.figma.service;

import org.example.figma.entity.Category;
import org.example.figma.model.dto.request.CategoryDto;
import org.example.figma.model.dto.request.CategoryEditDto;
import org.example.figma.model.dto.response.CategoryResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    ResponseEntity<List<CategoryResDto>> getCategories();
    List<Category> findAll();

    String saveCategory(CategoryDto categoryDto, MultipartFile multipartFile) throws IOException;

    String archiveCategory(UUID categoryId);

    String editCategory(CategoryEditDto categoryEditDto, MultipartFile multipartFile) throws IOException;
}
