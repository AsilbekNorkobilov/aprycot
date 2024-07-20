package org.example.figma.controller;

import lombok.RequiredArgsConstructor;
import org.example.figma.model.dto.request.*;
import org.example.figma.model.dto.response.CategoryResDto;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.model.dto.response.UserResDto;
import org.example.figma.service.CategoryService;
import org.example.figma.service.ProductService;
import org.example.figma.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("category") ResponseEntity<List<CategoryResDto>> getCategories(){return categoryService.getCategories();}

    @GetMapping("product") ResponseEntity<List<ProductResDto>> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("manager")ResponseEntity<List<UserResDto>> getManagers(){
        return userService.getMangers();
    }

    @PostMapping(value = "addManager", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getSavedManagerName(@RequestBody ManagerReqDto managerReqDto,@RequestBody MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(userService.saveManager(managerReqDto,multipartFile));
    }

    @PostMapping(value = "editManager",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editManager(@RequestBody ManagerEditReqDto managerEdit,@RequestBody MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(userService.editManager(managerEdit,multipartFile));
    }

    @PostMapping(value = "category", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getSavedCategoryName(@RequestBody CategoryDto categoryDto,@RequestBody MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(categoryService.saveCategory(categoryDto,multipartFile));
    }

    @PostMapping(value = "editCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editCategory(@RequestBody CategoryEditDto categoryEditDto, @RequestBody MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(categoryService.editCategory(categoryEditDto,multipartFile));
    }


    @PostMapping("category/delete")
    public ResponseEntity<?>deleteCategory(@RequestParam("categoryId") UUID categoryId){
        productService.archiveProductsByCategoryId(categoryId);
        return ResponseEntity.ok(categoryService.archiveCategory(categoryId));
    }

    @PostMapping(value = "product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getSavedProductName(@RequestBody ProductReqDto productReqDto, @RequestBody MultipartFile multipartFile ) throws IOException {
        return ResponseEntity.ok(productService.saveProduct(productReqDto,multipartFile));
    }

    @PostMapping(value = "editProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editProduct(@RequestBody ProductEditReqDto productEditReqDto,@RequestBody MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(productService.editProduct(productEditReqDto,multipartFile));
    }


    @PostMapping("product/delete")
    public ResponseEntity<?>deleteProduct(@RequestParam("productId") UUID productId){return ResponseEntity.ok(productService.archiveProduct(productId));}



}
