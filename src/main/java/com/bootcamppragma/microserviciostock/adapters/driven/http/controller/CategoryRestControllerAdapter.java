package com.bootcamppragma.microserviciostock.adapters.driven.http.controller;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddCategoryRequest;
import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.CategoryResponse;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryRequestMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryResponseMapper;
import com.bootcamppragma.microserviciostock.domain.api.ICategoryServicePort;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {

    private final ICategoryServicePort categoryServicePort;

    private final ICategoryRequestMapper categoryRequestMapper;

    private final ICategoryResponseMapper categoryResponseMapper;

    @Operation(summary = "create category")
    @PostMapping("/")
    public ResponseEntity<Void> addCategory(@RequestBody AddCategoryRequest request) {
        categoryServicePort.saveCategory(categoryRequestMapper.addRequestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Search category by name")
    @GetMapping("/{categoryName}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(categoryResponseMapper.toCategoryResponse(categoryServicePort.getCategory(categoryName)));
    }

    //hu2
    @Operation(summary = "Retrieve all categories in the order of the parameters")
    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestParam Integer page,
                                                                   @RequestParam Integer size,
                                                                   @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        return ResponseEntity.ok(categoryResponseMapper.
                toCategoryResponseList(categoryServicePort.getAllCategories(page, size, sortOrder)));
    }
}
