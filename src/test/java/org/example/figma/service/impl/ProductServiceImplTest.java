package org.example.figma.service.impl;

import org.example.figma.entity.Category;
import org.example.figma.entity.Product;
import org.example.figma.mappers.ProductMapper;
import org.example.figma.model.dto.response.CategoryResDto;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.model.dto.response.TrendingOrderDto;
import org.example.figma.repo.AttachmentRepository;
import org.example.figma.repo.ProductRepository;
import org.example.figma.service.AttachmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private AttachmentRepository attachmentRepository;
    private AttachmentService attachmentService;
    private ProductServiceImpl productService;

    @BeforeEach
    public void before(){
        productRepository= Mockito.mock(ProductRepository.class);
        productMapper=Mockito.mock(ProductMapper.class);
        attachmentRepository=Mockito.mock(AttachmentRepository.class);
        attachmentService=new AttachmentServiceImpl(attachmentRepository);
        productService=new ProductServiceImpl(productRepository,productMapper,attachmentService);

    }

    @Test
    void getAllProducts() {
        UUID id=UUID.randomUUID();
        List<Product> list=new ArrayList<>(List.of(Product.builder().id(id).build()));
        Mockito.when(productRepository.findAllByArchivedFalse()).thenReturn(new ArrayList<>(List.of(Product.builder().id(id).build())));
        ResponseEntity<List<ProductResDto>> temp = productService.getAllProducts();
        List<ProductResDto> body = temp.getBody();
        Assertions.assertEquals(id,body.get(0).getId());
    }

    @Test
    void findById() {
        UUID id=UUID.randomUUID();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(Product.builder().id(id).build()));
        Product byId = productService.findById(id);
        Assertions.assertEquals(id,byId.getId());
    }

    @Test
    void productNotFound(){
        UUID id=UUID.randomUUID();
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class,()->{
            productService.findById(id);
        });
    }

    @Test
    void findAll() {
        UUID id=UUID.randomUUID();
        List<Product> list=new ArrayList<>(List.of(Product.builder().id(id).build()));
        Mockito.when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(Product.builder().id(id).build())));
        List<Product> all = productService.findAll();
        Assertions.assertEquals(list.get(0).getId(),all.get(0).getId());
    }

    @Test
    void getTrendingProducts() {
        UUID id=UUID.randomUUID();
        TrendingOrderDto trendingOrderDto=new TrendingOrderDto();
        trendingOrderDto.setId(id);
        List<TrendingOrderDto> list=new ArrayList<>(List.of(trendingOrderDto));
        Mockito.when(productRepository.getTrendingProducts()).thenReturn(new ArrayList<>(List.of(trendingOrderDto)));
        ResponseEntity<List<TrendingOrderDto>> temp = productService.getTrendingProducts();
        List<TrendingOrderDto> body = temp.getBody();
        Assertions.assertEquals(id,body.get(0).getId());
    }
}