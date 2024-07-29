package org.example.figma.controller;

import lombok.RequiredArgsConstructor;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.model.dto.response.TrendingOrderDto;
import org.example.figma.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    ResponseEntity<List<ProductResDto>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/trending")
    ResponseEntity<List<TrendingOrderDto>> getTrendingProducts() {
        return productService.getTrendingProducts();
    }

    @GetMapping("search")
    public ResponseEntity<?> getProductsBySearch(@RequestParam String example){
        List<ProductResDto> productResDtos=productService.search(example);
        return ResponseEntity.ok(productResDtos);
    }
}
