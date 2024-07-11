package org.example.figma.controller;

import lombok.RequiredArgsConstructor;
import org.example.figma.model.dto.response.CategoryResDto;
import org.example.figma.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResDto>> getCategories() {
        return categoryService.getCategories();
    }
}
