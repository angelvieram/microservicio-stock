package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.adapter;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryAdapterTest {

    private ICategoryRepository categoryRepository;
    private ICategoryEntityMapper categoryEntityMapper;
    private CategoryAdapter categoryAdapter;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(ICategoryRepository.class);
        categoryEntityMapper = Mockito.mock(ICategoryEntityMapper.class);
        categoryAdapter = new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    @DisplayName("Should save category successfully when it doesn't already exist")
    void givenValidCategory_whenSaveCategory_thenCategoryIsSaved() {
        // Given
        Category category = new Category(1L, "Electronics", "Category for electronic items");
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);

        // When
        categoryAdapter.saveCategory(category);

        // Then
        verify(categoryRepository).save(categoryEntity);
    }

    @Test
    @DisplayName("Should throw CategoryAlreadyExistsException when the category already exists")
    void givenExistingCategory_whenSaveCategory_thenThrowCategoryAlreadyExistsException() {
        // Given
        Category category = new Category(1L, "Electronics", "Category for electronic items");
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(new CategoryEntity()));

        // When / Then
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryAdapter.saveCategory(category));
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }

    @Test
    @DisplayName("Should return the correct category when given a name")
    void givenCategoryName_whenGetCategory_thenReturnCategory() {
        // Given
        String categoryName = "Electronics";
        CategoryEntity categoryEntity = new CategoryEntity();
        Category expectedCategory = new Category(1L, categoryName, "Category for electronic items");
        when(categoryRepository.findByNameContaining(categoryName)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toModel(categoryEntity)).thenReturn(expectedCategory);

        // When
        Category actualCategory = categoryAdapter.getCategory(categoryName);

        // Then
        assertEquals(expectedCategory, actualCategory);
        verify(categoryRepository).findByNameContaining(categoryName);
    }

    @Test
    @DisplayName("Should throw ElementNotFoundException when category is not found by name")
    void givenNonExistingCategoryName_whenGetCategory_thenThrowElementNotFoundException() {
        // Given
        String categoryName = "NonExistingCategory";
        when(categoryRepository.findByNameContaining(categoryName)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ElementNotFoundException.class, () -> categoryAdapter.getCategory(categoryName));
        verify(categoryRepository).findByNameContaining(categoryName);
    }

    @Test
    @DisplayName("Should return paginated categories when pagination parameters are provided")
    void givenPaginationParams_whenGetAllCategories_thenReturnPaginatedCategories() {
        // Given
        Integer page = 0;
        Integer size = 10;
        String sortOrder = "asc";
        CategoryEntity categoryEntity = new CategoryEntity();
        Category category = new Category(1L, "Electronics", "Category for electronic items");
        Page<CategoryEntity> categoryEntityPage = new PageImpl<>(List.of(categoryEntity));
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryEntityPage);
        when(categoryEntityMapper.toModel(categoryEntity)).thenReturn(category);

        // When
        Pagination<Category> pagination = categoryAdapter.getAllCategories(page, size, sortOrder);

        // Then
        assertNotNull(pagination);
        assertEquals(1, pagination.getContent().size());
        assertEquals(category, pagination.getContent().get(0));
        verify(categoryRepository).findAll(any(Pageable.class));
    }
}