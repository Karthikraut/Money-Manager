package com.karthik.moneymanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.karthik.moneymanager.dto.CategoryDto;
import com.karthik.moneymanager.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.saveCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategoriesForCurrentUser() {
        List<CategoryDto> categories = categoryService.getCategoriesForCurrentUser();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<CategoryDto>> getCategoriesByTypeForCurrentUser(@PathVariable String type) {
        List<CategoryDto> list = categoryService.getCategoriesByTypeForCurrentUser(type);
        return ResponseEntity.ok(list);
    }

    @PutMapping ("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto dto) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryId, dto);
        return ResponseEntity.ok(updatedCategory);
    }
}

// The `@PathVariable String type` in the provided code snippet is used to extract a value from the URL path and assign it to the method parameter `type`.

// In the example:

// ```java
// @GetMapping("/{type}")
// public List getCategoriesByTypeForCurrentUser(@PathVariable String type) {
//     return categoryService.getCategoriesForCurrentUser(type);
// }
// ```

// - The `@GetMapping("/{type}")` annotation tells Spring that this method should handle HTTP GET requests to endpoints such as `/food`, `/travel`, `/work`, etc., where `{type}` is a placeholder in the URL path.
// - The `@PathVariable String type` parameter instructs Spring to take whatever value is present in the place of `{type}` in the URL and assign it to the `type` variable.

// **Example in practice:**
// - If a client makes a GET request to `/category/food`, then `type` will be `"food"`.
// - If the request is to `/category/travel`, then `type` will be `"travel"`.

// This allows your method to access user-provided path data in a type-safe and straightforward way, letting you handle requests dynamically based on the URL segment provided.
