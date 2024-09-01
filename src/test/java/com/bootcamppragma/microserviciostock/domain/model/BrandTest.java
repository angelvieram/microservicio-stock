package com.bootcamppragma.microserviciostock.domain.model;

import com.bootcamppragma.microserviciostock.domain.exception.EmptyFieldException;
import com.bootcamppragma.microserviciostock.domain.exception.InvalidBrandLengthException;
import com.bootcamppragma.microserviciostock.domain.util.DomainConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrandTest {

    @Test
    @DisplayName("Dada una marca válida, se debe crear correctamente")
    void createBrandSuccessfully() {
        // GIVEN
        Long id = 1L;
        String name = "Samsung";
        String description = "A global leader in electronics and technology.";

        // WHEN
        Brand brand = new Brand(id, name, description);

        // THEN
        assertEquals(id, brand.getId());
        assertEquals(name, brand.getName());
        assertEquals(description, brand.getDescription());
    }

    @Test
    @DisplayName("Cuando el nombre de la marca está vacío, debería lanzar EmptyFieldException")
    void createBrandWithEmptyNameThrowsException() {
        // GIVEN
        String name = "";
        String description = "A global leader in electronics and technology.";

        // WHEN & THEN
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () ->
                new Brand(1L, name, description)
        );

        assertEquals(DomainConstants.Field.NAME.toString(), exception.getMessage());
    }

    @Test
    @DisplayName("Cuando la descripción de la marca está vacía, debería lanzar EmptyFieldException")
    void createBrandWithEmptyDescriptionThrowsException() {
        // GIVEN
        String name = "Samsung";
        String description = "";

        // WHEN & THEN
        EmptyFieldException exception = assertThrows(EmptyFieldException.class, () ->
                new Brand(1L, name, description)
        );

        assertEquals(DomainConstants.Field.DESCRIPTION.toString(), exception.getMessage());
    }

    @Test
    @DisplayName("Cuando el nombre de la marca excede la longitud máxima permitida, debería lanzar InvalidBrandLengthException")
    void createBrandWithTooLongNameThrowsException() {
        // GIVEN
        String name = "A".repeat(DomainConstants.MAX_BRAND_NAME_LENGTH + 1);
        String description = "A global leader in electronics and technology.";

        // WHEN & THEN
        InvalidBrandLengthException exception = assertThrows(InvalidBrandLengthException.class, () ->
                new Brand(1L, name, description)
        );

        assertEquals(DomainConstants.Field.NAME.toString(), exception.getMessage());
    }

    @Test
    @DisplayName("Cuando la descripción de la marca excede la longitud máxima permitida, debería lanzar InvalidBrandLengthException")
    void createBrandWithTooLongDescriptionThrowsException() {
        // GIVEN
        String name = "Samsung";
        String description = "A".repeat(DomainConstants.MAX_BRAND_DESCRIPTION_LENGTH + 1);

        // WHEN & THEN
        InvalidBrandLengthException exception = assertThrows(InvalidBrandLengthException.class, () ->
                new Brand(1L, name, description)
        );

        assertEquals(DomainConstants.Field.DESCRIPTION.toString(), exception.getMessage());
    }
}
