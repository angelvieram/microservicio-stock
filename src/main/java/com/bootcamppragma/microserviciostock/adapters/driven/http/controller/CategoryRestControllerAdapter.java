package com.bootcamppragma.microserviciostock.adapters.driven.http.controller;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddCategoryRequest;
import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.CategoryResponse;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryRequestMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryResponseMapper;
import com.bootcamppragma.microserviciostock.domain.api.ICategoryServicePort;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {

    private final ICategoryServicePort categoryServicePort;

    private final ICategoryRequestMapper categoryRequestMapper;

    private final ICategoryResponseMapper categoryResponseMapper;

    @PostMapping
    public ResponseEntity<Void> addCategory(@RequestBody AddCategoryRequest request) {
        categoryServicePort.saveCategory(categoryRequestMapper.addRequestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(categoryResponseMapper.toCategoryResponse(categoryServicePort.getCategory(categoryName)));
    }
}
