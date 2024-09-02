package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.adapter;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryAdapter categoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería guardar una categoría si no existe")
    void saveCategory() {
        // GIVEN
        Category category = new Category(1L, "Electronics", "Electronic gadgets");
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronic gadgets");

        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);

        // WHEN
        categoryAdapter.saveCategory(category);

        // THEN
        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    @DisplayName("Debería lanzar CategoryAlreadyExistsException si la categoría ya existe")
    void saveCategory_shouldThrowException_whenCategoryExists() {
        // GIVEN
        Category category = new Category(1L, "Electronics", "Electronic gadgets");
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(new CategoryEntity()));

        // WHEN & THEN
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryAdapter.saveCategory(category));
    }

    @Test
    @DisplayName("Debería obtener una categoría por su nombre")
    void getCategory() {
        // GIVEN
        String name = "Electronics";
        CategoryEntity categoryEntity = new CategoryEntity(1L, name, "Electronic gadgets");
        Category category = new Category(1L, name, "Electronic gadgets");

        when(categoryRepository.findByNameContaining(name)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toModel(categoryEntity)).thenReturn(category);

        // WHEN
        Category result = categoryAdapter.getCategory(name);

        // THEN
        assertEquals(category, result);
    }

    @Test
    @DisplayName("Debería lanzar ElementNotFoundException si la categoría no existe")
    void getCategory_shouldThrowException_whenCategoryNotFound() {
        // GIVEN
        String name = "NonExistentCategory";
        when(categoryRepository.findByNameContaining(name)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ElementNotFoundException.class, () -> categoryAdapter.getCategory(name));
    }

    @Test
    @DisplayName("Debería obtener todas las categorías con paginación y orden ascendente")
    void getAllCategories() {
        // GIVEN
        CategoryEntity categoryEntity1 = new CategoryEntity(1L, "Electronics", "Electronic gadgets");
        CategoryEntity categoryEntity2 = new CategoryEntity(2L, "Books", "Fiction and non-fiction books");
        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(categoryEntities));
        when(categoryEntityMapper.toModelList(categoryEntities)).thenReturn(List.of(
                new Category(1L, "Electronics", "Electronic gadgets"),
                new Category(2L, "Books", "Fiction and non-fiction books")
        ));

        // WHEN
        List<Category> result = categoryAdapter.getAllCategories(0, 10, "asc");

        // THEN
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Debería lanzar NoDataFoundException si no se encuentran categorías")
    void getAllCategories_shouldThrowException_whenNoCategoriesFound() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of()));

        // WHEN & THEN
        assertThrows(NoDataFoundException.class, () -> categoryAdapter.getAllCategories(0, 10, "asc"));
    }
}
