package com.bootcamppragma.microserviciostock.domain.api.usecase;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.bootcamppragma.microserviciostock.domain.spi.ICategoryPersistencePort;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryUseCaseTest {

    private ICategoryPersistencePort categoryPersistencePort;
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        categoryPersistencePort = Mockito.mock(ICategoryPersistencePort.class);
        categoryUseCase = new CategoryUseCase(categoryPersistencePort);
    }

    @Test
    @DisplayName("Should save category successfully when given a valid category")
    void givenValidCategory_whenSaveCategory_thenCategoryIsSaved() {
        // Given
        Category category = new Category(1L, "Electronics", "Category for electronic items");

        // When
        categoryUseCase.saveCategory(category);

        // Then
        verify(categoryPersistencePort).saveCategory(category);
    }

    @Test
    @DisplayName("Should return the correct category when given a category name")
    void givenCategoryName_whenGetCategory_thenReturnCategory() {
        // Given
        String categoryName = "Electronics";
        Category expectedCategory = new Category(1L, categoryName, "Category for electronic items");
        when(categoryPersistencePort.getCategory(categoryName)).thenReturn(expectedCategory);

        // When
        Category actualCategory = categoryUseCase.getCategory(categoryName);

        // Then
        assertEquals(expectedCategory, actualCategory);
        verify(categoryPersistencePort).getCategory(categoryName);
    }

    @Test
    @DisplayName("Should return paginated categories when pagination parameters are provided")
    void givenPaginationParams_whenGetAllCategories_thenReturnPaginatedCategories() {
        // Given
        Integer page = 0;
        Integer size = 10;
        String sortOrder = "asc";
        Pagination<Category> expectedPagination = new Pagination<>(
                List.of(new Category(1L, "Electronics", "Category for electronic items")),
                page, size, 1L, 1);

        when(categoryPersistencePort.getAllCategories(page, size, sortOrder)).thenReturn(expectedPagination);

        // When
        Pagination<Category> actualPagination = categoryUseCase.getAllCategories(page, size, sortOrder);

        // Then
        assertEquals(expectedPagination, actualPagination);
        verify(categoryPersistencePort).getAllCategories(page, size, sortOrder);
    }
}