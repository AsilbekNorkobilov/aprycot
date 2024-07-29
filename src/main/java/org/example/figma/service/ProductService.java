package org.example.figma.service;

import org.example.figma.entity.Product;
import org.example.figma.model.dto.request.ProductEditReqDto;
import org.example.figma.model.dto.request.ProductReqDto;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.model.dto.response.TrendingOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ResponseEntity<List<ProductResDto>> getAllProducts();

    Product findById(UUID id);

    List<Product> findAll();

    ResponseEntity<List<TrendingOrderDto>> getTrendingProducts();

    String saveProduct(ProductReqDto productReqDto, MultipartFile multipartFile) throws IOException;

    void archiveProductsByCategoryId(UUID categoryId);

    String archiveProduct(UUID productId);

    String editProduct(ProductEditReqDto productEditReqDto, MultipartFile multipartFile) throws IOException;

    ProductResDto getProductById(UUID id);

    List<ProductResDto> search(String example);
}
