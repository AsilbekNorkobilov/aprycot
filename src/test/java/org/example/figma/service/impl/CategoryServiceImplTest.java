package org.example.figma.service.impl;

import org.example.figma.entity.Category;
import org.example.figma.mappers.CategoryMapper;
import org.example.figma.model.dto.response.CategoryResDto;
import org.example.figma.repo.AttachmentRepository;
import org.example.figma.repo.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class CategoryServiceImplTest {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;
    private AttachmentRepository attachmentRepository;
    private AttachmentServiceImpl attachmentService;

    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void before(){
        categoryRepository= Mockito.mock(CategoryRepository.class);
        categoryMapper=Mockito.mock(CategoryMapper.class);
        attachmentRepository=Mockito.mock(AttachmentRepository.class);
        attachmentService=new AttachmentServiceImpl(attachmentRepository);
        categoryService=new CategoryServiceImpl(categoryRepository,categoryMapper,attachmentService);
    }

    @Test
    void getCategories() {
        UUID id=UUID.randomUUID();
        List<Category> categories=new ArrayList<>(List.of(Category.builder().id(id).build()));
        Mockito.when(categoryRepository.findAllByArchivedFalse()).thenReturn(new ArrayList<>(List.of(Category.builder().id(id).build())));
        ResponseEntity<List<CategoryResDto>> temp = categoryService.getCategories();
        List<CategoryResDto> body = temp.getBody();
        Assertions.assertEquals(categories.get(0).getId(),body.get(0).getId());
    }

    @Test
    void findAll() {
        UUID id=UUID.randomUUID();
        List<Category> categories=new ArrayList<>(List.of(Category.builder().id(id).build()));
        Mockito.when(categoryRepository.findAll()).thenReturn(new ArrayList<>(List.of(Category.builder().id(id).build())));
        List<Category> all = categoryService.findAll();
        Assertions.assertEquals(categories.get(0).getId(),all.get(0).getId());

    }

    @Test
    void saveCategory() {

    }


}