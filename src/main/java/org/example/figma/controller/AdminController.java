package org.example.figma.controller;

import lombok.RequiredArgsConstructor;
import org.example.figma.model.dto.forsave.CategoryDto;
import org.example.figma.model.dto.forsave.MangerUUIDPhotoDto;
import org.example.figma.model.dto.forsave.ProductDto;
import org.example.figma.model.dto.response.CategoryResDto;
import org.example.figma.model.dto.forsave.ManagerResDto;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.model.dto.response.UserResDto;
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
@RequestMapping("/api/admin")
public class AdminController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("category") ResponseEntity<List<CategoryResDto>> getCategories(){
        return categoryService.getCategories();
    }
    @GetMapping("product") ResponseEntity<List<ProductResDto>> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("manager")ResponseEntity<List<UserResDto>> getManagers(){
        return userService.getMangers();
    }

    @PostMapping("addManager")
    public UUID getSavedManagerId(@RequestBody ManagerResDto managerResDto){
        return userService.saveManager(managerResDto);
    }
    @PostMapping("addManager/photo")
    public ResponseEntity<?> savePhoto(@RequestBody MangerUUIDPhotoDto manager) throws IOException {
        return ResponseEntity.ok(userService.saveManagerPhoto(manager));
    }

    @PostMapping("category")
    public UUID getSavedCategoryId(@RequestParam String name){
        return categoryService.saveCategory(name);
    }
    @PostMapping("category/photo")
    public ResponseEntity<?> saveCategoryPhoto(@RequestBody CategoryDto categoryDto) throws IOException {
        return ResponseEntity.ok(categoryService.saveCategoryPhoto(categoryDto));
    }

    @PostMapping("product")
    public UUID getSavedProductId(@RequestBody ProductResDto productResDto){
        return productService.saveProductReturnId(productResDto);
    }
    @PostMapping("product/photo")
    public ResponseEntity<?> saveProductPhoto(@RequestBody ProductDto productDto) throws IOException {
        return ResponseEntity.ok(productService.saveProductPhoto(productDto));
    }

}
