package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.Attachment;
import org.example.figma.entity.Category;
import org.example.figma.entity.Product;
import org.example.figma.mappers.ProductMapper;
import org.example.figma.model.dto.forsave.ProductDto;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.model.dto.response.TrendingOrderDto;
import org.example.figma.repo.CategoryRepository;
import org.example.figma.repo.ProductRepository;
import org.example.figma.service.AttachmentService;
import org.example.figma.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AttachmentService attachmentService;
    private final CategoryRepository categoryRepository;

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

    @Override
    public UUID saveProductReturnId(ProductResDto productResDto){
        Category category = categoryRepository.findByName(productResDto.getCategoryName());
        Product product = Product.builder()
                .name(productResDto.getName())
                .price(productResDto.getPrice())
                .calorie(productResDto.getCalorie())
                .isArchived(false)
                .category(category)
                .build();
        productRepository.save(product);
        return product.getId();
    }

    @Override
    public String saveProductPhoto(ProductDto productDto) throws IOException {
        Product product = productRepository.findById(productDto.getId()).get();
        Attachment attachment = attachmentService.savePhoto(productDto.getMultipartFile().getBytes());
        product.setAttachment(attachment);
        productRepository.save(product);
        return "Product saved! ProductName: "+product.getName();
    }
}
