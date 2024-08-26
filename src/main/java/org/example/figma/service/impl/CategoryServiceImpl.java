package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.Attachment;
import org.example.figma.entity.Category;
import org.example.figma.mappers.CategoryMapper;
import org.example.figma.model.dto.request.CategoryDto;
import org.example.figma.model.dto.request.CategoryEditDto;
import org.example.figma.model.dto.response.CategoryResDto;
import org.example.figma.repo.AttachmentRepository;
import org.example.figma.repo.CategoryRepository;
import org.example.figma.service.AttachmentService;
import org.example.figma.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final AttachmentRepository attachmentRepository;

    @Override
    public ResponseEntity<List<CategoryResDto>> getCategories() {
        List<Category> categories = categoryRepository.findAllByArchivedFalse();
        List<CategoryResDto> categoryResDtos = categories.stream().map(category -> {
            CategoryResDto categoryDto = categoryMapper.toDto(category);
            String base64Photo = Base64.getEncoder().encodeToString(attachmentService.findById(category.getAttachment().getId()).getFullImage());
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
    public String saveCategory(CategoryDto categoryDto, MultipartFile multipartFile) throws IOException {
        Attachment attachment = attachmentService.savePhoto(multipartFile.getBytes());
        Category category = Category.builder()
                .name(categoryDto.getName())
                .isArchived(false)
                .attachment(attachment)
                .build();
        Category currnetCategory = categoryRepository.save(category);
        return "Category saved! CategoryName: "+ currnetCategory.getName();
    }

    @Override
    public String editCategory(CategoryEditDto categoryEditDto, MultipartFile multipartFile) throws IOException {
        Category currentCategory = categoryRepository.findById(categoryEditDto.getId()).get();

        UUID currentCategoryAttachId = currentCategory.getAttachment().getId();

        if(!multipartFile.isEmpty()){
            Attachment attachment = attachmentService.savePhoto(multipartFile.getBytes());
            currentCategory.setAttachment(attachment);
        }
        currentCategory.setName(categoryEditDto.getName());
        categoryRepository.save(currentCategory);
        attachmentService.deleteAttachment(currentCategoryAttachId);
        return "Category is edited! CategoryName: "+ currentCategory.getName();
    }

    @Override
    public String archiveCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setArchived(true);
        categoryRepository.save(category);
        return "Category is deleted! CategoryName: "+category.getName();
    }
}
