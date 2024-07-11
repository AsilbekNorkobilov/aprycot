package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.Product;
import org.example.figma.mappers.ProductMapper;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.model.dto.response.TrendingOrderDto;
import org.example.figma.repo.ProductRepository;
import org.example.figma.service.AttachmentService;
import org.example.figma.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AttachmentService attachmentService;

    @Override
    public ResponseEntity<List<ProductResDto>> getAllProducts() {
        List<ProductResDto> productResDtoList = productRepository.findAllByArchivedFalse().stream().map(product -> {
            ProductResDto dto = productMapper.toDto(product);
            String base64Photo = Base64.getEncoder().encodeToString(attachmentService.findById(product.getAttachment().getId()).getPressedImage());
            String category = product.getCategory().getName();
            dto.setBase64Photo(base64Photo);
            dto.setCategoryName(category);
            return dto;
        }).toList();
        return ResponseEntity.ok(productResDtoList);
    }

    @Override
    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<List<TrendingOrderDto>> getTrendingProducts() {
        List<TrendingOrderDto> trendingProducts = productRepository.getTrendingProducts();
        return ResponseEntity.ok(trendingProducts);
    }
}
