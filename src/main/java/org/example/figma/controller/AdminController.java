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
    public String getSavedManagerName(@RequestBody ManagerReqDto managerReqDto,@RequestBody MultipartFile multipartFile) throws IOException {
        return userService.saveManager(managerReqDto,multipartFile);
    }

    @PostMapping(value = "editManager",)


    @PostMapping(value = "category", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String getSavedCategoryName(@RequestParam("categoryName") String categoryName,@RequestBody MultipartFile multipartFile) throws IOException {
        return categoryService.saveCategory(categoryName,multipartFile);
    }

    @PostMapping("category/delete")
    public ResponseEntity<?>deleteCategory(@RequestParam("categoryId") UUID categoryId){
        productService.archiveProductsByCategoryId(categoryId);
        return ResponseEntity.ok(categoryService.archiveCategory(categoryId));
    }

    @PostMapping(value = "product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String getSavedProductName(@RequestBody ProductReqDto productReqDto, @RequestBody MultipartFile multipartFile ) throws IOException {
        return productService.saveProduct(productReqDto,multipartFile);
    }


    @PostMapping("product/delete")
    public ResponseEntity<?>deleteProduct(@RequestParam("productId") UUID productId){return ResponseEntity.ok(productService.archiveProduct(productId));}



}
