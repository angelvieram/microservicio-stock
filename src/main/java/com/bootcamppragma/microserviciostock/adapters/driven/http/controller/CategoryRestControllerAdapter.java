package com.bootcamppragma.microserviciostock.adapters.driven.http.controller;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddCategoryRequest;
import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.CategoryResponse;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryRequestMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryResponseMapper;
import com.bootcamppragma.microserviciostock.domain.api.ICategoryServicePort;

import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Operation(summary = "Retrieve all categories with pagination")
    @GetMapping("/")
    public ResponseEntity<Pagination<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        // Obtén la información de paginación del servicio
        Pagination<Category> categoryPage = categoryServicePort.getAllCategories(page, size, sortOrder);

        // Mapea la entidad a DTO
        List<CategoryResponse> categoryResponses = categoryPage.getContent().stream()
                .map(categoryResponseMapper::toCategoryResponse)
                .collect(Collectors.toList());

        // Crea el objeto Pagination para la respuesta
        Pagination<CategoryResponse> paginationResponse = new Pagination<>(
                categoryResponses,
                categoryPage.getPageNumber(),
                categoryPage.getPageSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages()
        );

        return ResponseEntity.ok(paginationResponse);
    }
}
