package org.example.figma.controller;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.User;
import org.example.figma.model.dto.response.CategoryResDto;
import org.example.figma.model.dto.response.ProductResDto;
import org.example.figma.model.dto.response.UserResDto;
import org.example.figma.service.CategoryService;
import org.example.figma.service.ProductService;
import org.example.figma.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
}
