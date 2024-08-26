package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.Attachment;
import org.example.figma.entity.Category;
import org.example.figma.entity.Product;
import org.example.figma.mappers.ProductMapper;
import org.example.figma.model.dto.request.ProductEditReqDto;
import org.example.figma.model.dto.request.ProductReqDto;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.model.dto.response.TrendingOrderDto;
import org.example.figma.repo.CategoryRepository;
import org.example.figma.repo.ProductRepository;
import org.example.figma.service.AttachmentService;
import org.example.figma.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            String base64Photo = Base64.getEncoder().encodeToString(attachmentService.findById(product.getAttachment().getId()).getFullImage());
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
    public String saveProduct(ProductReqDto productReqDto, MultipartFile multipartFile) throws IOException {
        Category category = categoryRepository.findByName(productReqDto.getCategoryName());
        Attachment attachment = attachmentService.savePhoto(multipartFile.getBytes());
        Product product = Product.builder()
                .name(productReqDto.getName())
                .price(productReqDto.getPrice())
                .calorie(productReqDto.getCalorie())
                .isArchived(false)
                .category(category)
                .attachment(attachment)
                .build();
        productRepository.save(product);
        return "Product saved! ProductName: "+product.getName();
    }

    @Override
    public String editProduct(ProductEditReqDto productEditReqDto, MultipartFile multipartFile) throws IOException {
        Product currnetProduct = productRepository.findById(productEditReqDto.getId()).get();
        UUID currentAttachId = currnetProduct.getAttachment().getId();

        if (!multipartFile.isEmpty()){
            Attachment attachment = attachmentService.savePhoto(multipartFile.getBytes());
            currnetProduct.setAttachment(attachment);
        }
        currnetProduct.setName(productEditReqDto.getName());
        currnetProduct.setPrice(productEditReqDto.getPrice());
        currnetProduct.setCalorie(productEditReqDto.getCalorie());
        productRepository.save(currnetProduct);
        attachmentService.deleteAttachment(currentAttachId);
        return "Product edited! ProductName: "+currnetProduct.getName();
    }

    @Override
    public void archiveProductsByCategoryId(UUID categoryId) {
        productRepository.archiveProducts(categoryId);
    }

    @Override
    public String archiveProduct(UUID productId) {
        productRepository.archiveProduct(productId);
        return "Product is deleted!";
    }
}
