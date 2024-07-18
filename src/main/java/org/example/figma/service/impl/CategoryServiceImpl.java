package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.Attachment;
import org.example.figma.entity.Category;
import org.example.figma.mappers.CategoryMapper;
import org.example.figma.model.dto.request.CategoryDto;
import org.example.figma.model.dto.response.CategoryResDto;
import org.example.figma.repo.CategoryRepository;
import org.example.figma.service.AttachmentService;
import org.example.figma.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AttachmentService attachmentService;

    @Override
    public ResponseEntity<List<CategoryResDto>> getCategories() {
        List<Category> categories = categoryRepository.findAllByArchivedFalse();
        List<CategoryResDto> categoryResDtos = categories.stream().map(category -> {
            CategoryResDto categoryDto = categoryMapper.toDto(category);
            String base64Photo = Base64.getEncoder().encodeToString(attachmentService.findById(category.getAttachment().getId()).getPressedImage());
            categoryDto.setBase64Photo(base64Photo);
            return categoryDto;
        }).toList();
        return ResponseEntity.status(200).body(categoryResDtos);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public UUID saveCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .isArchived(false)
                .build();
        Category currnetCategory = categoryRepository.save(category);
        return currnetCategory.getId();
    }

    @Override
    public String saveCategoryPhoto(CategoryDto categoryDto) throws IOException {
        Category currentCategory = categoryRepository.findById(categoryDto.getId()).get();
        Attachment attachment = attachmentService.savePhoto(categoryDto.getMultipartFile().getBytes());
        currentCategory.setAttachment(attachment);
        categoryRepository.save(currentCategory);
        return "Category saved! CategoryName: "+currentCategory.getName();
    }

    @Override
    public String archiveCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setArchived(true);
        categoryRepository.save(category);
        return "Category is deleted! CategoryName: "+category.getName();
    }
}
