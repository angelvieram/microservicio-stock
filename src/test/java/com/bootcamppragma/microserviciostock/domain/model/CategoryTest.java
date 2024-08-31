package com.bootcamppragma.microserviciostock.domain.model;

import com.bootcamppragma.microserviciostock.domain.exception.EmptyFieldException;
import com.bootcamppragma.microserviciostock.domain.exception.InvalidCategoryLengthException;
import com.bootcamppragma.microserviciostock.domain.util.DomainConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Category Validations Test")
class CategoryTest {
    @Test
    void shouldThrowExceptionForEmptyName() {
        assertThrows(EmptyFieldException.class, () -> {
            new Category(1L, " ", "Valid description");
        });
    }

    @Test
    void shouldThrowExceptionForEmptyDescription() {
        assertThrows(EmptyFieldException.class, () -> {
            new Category(1L, "Valid name", " ");
        });
    }

    @Test
    void shouldThrowExceptionForLongName() {
        String longName = "a".repeat(DomainConstants.MAX_CATEGORY_NAME_LENGTH + 1);
        assertThrows(InvalidCategoryLengthException.class, () -> {
            new Category(1L, longName, "Valid description");
        });
    }

    @Test
    void shouldThrowExceptionForLongDescription() {
        String longDescription = "a".repeat(DomainConstants.MAX_CATEGORY_DESCRIPTION_LENGTH + 1);
        assertThrows(InvalidCategoryLengthException.class, () -> {
            new Category(1L, "Valid name", longDescription);
        });
    }

    @Test
    void shouldThrowExceptionForNullName() {
        assertThrows(NullPointerException.class, () -> {
            new Category(1L, null, "Valid description");
        });
    }

    @Test
    void shouldThrowExceptionForNullDescription() {
        assertThrows(NullPointerException.class, () -> {
            new Category(1L, "Valid name", null);
        });
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        Category category = new Category(1L, "Valid name", "Valid description");
        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("Valid name", category.getName());
        assertEquals("Valid description", category.getDescription());
    }

}