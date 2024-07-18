package org.example.figma.controller;

import lombok.RequiredArgsConstructor;
import org.example.figma.model.dto.request.CategoryDto;
import org.example.figma.model.dto.request.ProductDto;
import org.example.figma.model.dto.response.CategoryResDto;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.service.CategoryService;
import org.example.figma.service.ProductService;
import org.example.figma.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
public class ManagerController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("category")
    ResponseEntity<List<CategoryResDto>> getCategories(){return categoryService.getCategories();}

    @GetMapping("product") ResponseEntity<List<ProductResDto>> getProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("category")
    public UUID getSavedCategoryId(@RequestParam("categoryName") String categoryName){return categoryService.saveCategory(categoryName);}

    @PostMapping("category/photo")
    public ResponseEntity<?> saveCategoryPhoto(@RequestBody CategoryDto categoryDto) throws IOException {return ResponseEntity.ok(categoryService.saveCategoryPhoto(categoryDto));}

    @PostMapping("category/delete")
    public ResponseEntity<?>deleteCategory(@RequestParam("categoryId") UUID categoryId){
        productService.archiveProductsByCategoryId(categoryId);
        return ResponseEntity.ok(categoryService.archiveCategory(categoryId));
    }

    @PostMapping("product")
    public UUID getSavedProductId(@RequestBody ProductResDto productResDto){return productService.saveProductReturnId(productResDto);}

    @PostMapping("product/photo")
    public ResponseEntity<?> saveProductPhoto(@RequestBody ProductDto productDto) throws IOException {return ResponseEntity.ok(productService.saveProductPhoto(productDto));}

    @PostMapping("product/delete")
    public ResponseEntity<?>deleteProduct(@RequestParam("productId") UUID productId){return ResponseEntity.ok(productService.archiveProduct(productId));}



}
