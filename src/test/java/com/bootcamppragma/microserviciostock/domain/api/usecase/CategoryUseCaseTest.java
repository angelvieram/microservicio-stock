package com.bootcamppragma.microserviciostock.domain.api.usecase;

import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.bootcamppragma.microserviciostock.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @ExtendWith(MockitoExtension.class)
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    @DisplayName("Dada una categoria debe guardarla correctamente en la bd")
    void saveCategory() {
        //GIVEN
        Category category = new Category(1L,"Electronics", "Devices and gadgets");

        //WHEN
        categoryUseCase.saveCategory(category);

        //THEN
        verify(categoryPersistencePort, times(1)).saveCategory(category);

    }

    //hu1
    @Test
    @DisplayName("Given a valid category name, when retrieving the category, then it should return the correct category")
    void getCategory() {

        String categoryName = "Electronics";
        Category expectedCategory = new Category(1L,categoryName, "Devices and gadgets");
        when(categoryPersistencePort.getCategory(categoryName)).thenReturn(expectedCategory);

        Category actualCategory = categoryUseCase.getCategory(categoryName);

        assertNotNull(actualCategory);
        assertEquals(expectedCategory, actualCategory);
        verify(categoryPersistencePort, times(1)).getCategory(categoryName);

    }

    //hu2
    @Test
    @DisplayName("Dado parámetros válidos, debe retornar una lista de categorías")
    void getAllCategories() {
        // GIVEN
        int page = 0;
        int size = 10;
        String sortOrder = "asc";
        List<Category> expectedCategories = List.of(
                new Category(1L, "Electronics", "Devices and gadgets"),
                new Category(2L, "Books", "Various books")
        );

        when(categoryPersistencePort.getAllCategories(page, size, sortOrder)).thenReturn(expectedCategories);

        // WHEN
        List<Category> actualCategories = categoryUseCase.getAllCategories(page, size, sortOrder);

        // THEN
        assertNotNull(actualCategories);
        assertEquals(expectedCategories, actualCategories);
        verify(categoryPersistencePort, times(1)).getAllCategories(page, size, sortOrder);
    }

}