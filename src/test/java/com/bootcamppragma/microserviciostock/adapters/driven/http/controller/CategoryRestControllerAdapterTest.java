package com.bootcamppragma.microserviciostock.adapters.driven.http.controller;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddCategoryRequest;
import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.CategoryResponse;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryRequestMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.ICategoryResponseMapper;
import com.bootcamppragma.microserviciostock.domain.api.ICategoryServicePort;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryRestControllerAdapterTest {

    private ICategoryServicePort categoryServicePort;
    private ICategoryRequestMapper categoryRequestMapper;
    private ICategoryResponseMapper categoryResponseMapper;
    private CategoryRestControllerAdapter categoryRestControllerAdapter;

    @BeforeEach
    void setUp() {
        categoryServicePort = Mockito.mock(ICategoryServicePort.class);
        categoryRequestMapper = Mockito.mock(ICategoryRequestMapper.class);
        categoryResponseMapper = Mockito.mock(ICategoryResponseMapper.class);
        categoryRestControllerAdapter = new CategoryRestControllerAdapter(categoryServicePort, categoryRequestMapper, categoryResponseMapper);
    }

    @Test
    @DisplayName("Should create category successfully")
    void givenValidCategoryRequest_whenAddCategory_thenReturnCreatedStatus() {
        // Given
        AddCategoryRequest request = new AddCategoryRequest("Electronics", "Category for electronic items");
        Category category = new Category(1L, "Electronics", "Category for electronic items");
        when(categoryRequestMapper.addRequestToCategory(request)).thenReturn(category);

        // When
        ResponseEntity<Void> response = categoryRestControllerAdapter.addCategory(request);

        // Then
        verify(categoryServicePort).saveCategory(category);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return category by name successfully")
    void givenCategoryName_whenGetCategory_thenReturnCategoryResponse() {
        // Given
        String categoryName = "Electronics";
        Category category = new Category(1L, categoryName, "Category for electronic items");
        CategoryResponse expectedResponse = new CategoryResponse(1L, categoryName, "Category for electronic items");

        when(categoryServicePort.getCategory(categoryName)).thenReturn(category);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(expectedResponse);

        // When
        ResponseEntity<CategoryResponse> response = categoryRestControllerAdapter.getCategory(categoryName);

        // Then
        verify(categoryServicePort).getCategory(categoryName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @DisplayName("Should return paginated categories successfully")
    void givenPaginationParams_whenGetAllCategories_thenReturnPaginatedCategories() {
        // Given
        Integer page = 0;
        Integer size = 10;
        String sortOrder = "asc";

        Category category = new Category(1L, "Electronics", "Category for electronic items");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "Electronics", "Category for electronic items");

        Pagination<Category> categoryPagination = new Pagination<>(
                List.of(category), page, size, 1L, 1
        );

        when(categoryServicePort.getAllCategories(page, size, sortOrder)).thenReturn(categoryPagination);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        // When
        ResponseEntity<Pagination<CategoryResponse>> response = categoryRestControllerAdapter.getAllCategories(page, size, sortOrder);

        // Then
        verify(categoryServicePort).getAllCategories(page, size, sortOrder);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(categoryResponse, response.getBody().getContent().get(0));
    }
}