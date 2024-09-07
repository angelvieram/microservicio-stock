package com.bootcamppragma.microserviciostock.domain.api.usecase;

import com.bootcamppragma.microserviciostock.domain.model.Brand;
import com.bootcamppragma.microserviciostock.domain.spi.IBrandPersistencePort;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrandUseCaseTest {

    private IBrandPersistencePort brandPersistencePort;
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        brandPersistencePort = Mockito.mock(IBrandPersistencePort.class);
        brandUseCase = new BrandUseCase(brandPersistencePort);
    }

    @Test
    @DisplayName("Should save brand successfully when given a valid brand")
    void givenValidBrand_whenSaveBrand_thenBrandIsSaved() {
        // Given
        Brand brand = new Brand(1L, "Nike", "Sports brand");

        // When
        brandUseCase.saveBrand(brand);

        // Then
        verify(brandPersistencePort).saveBrand(brand);
    }

    @Test
    @DisplayName("Should return the correct brand when given a brand name")
    void givenBrandName_whenGetBrand_thenReturnBrand() {
        // Given
        String brandName = "Nike";
        Brand expectedBrand = new Brand(1L, brandName, "Sports brand");
        when(brandPersistencePort.getBrand(brandName)).thenReturn(expectedBrand);

        // When
        Brand actualBrand = brandUseCase.getBrand(brandName);

        // Then
        assertEquals(expectedBrand, actualBrand);
        verify(brandPersistencePort).getBrand(brandName);
    }

    @Test
    @DisplayName("Should return paginated brands when pagination parameters are provided")
    void givenPaginationParams_whenGetAllBrands_thenReturnPaginatedBrands() {
        // Given
        Integer page = 0;
        Integer size = 10;
        String sortOrder = "asc";
        Pagination<Brand> expectedPagination = new Pagination<>(
                List.of(new Brand(1L, "Nike", "Sports brand")),
                page, size, 1L, 1);

        when(brandPersistencePort.getAllBrands(page, size, sortOrder)).thenReturn(expectedPagination);

        // When
        Pagination<Brand> actualPagination = brandUseCase.getAllBrands(page, size, sortOrder);

        // Then
        assertEquals(expectedPagination, actualPagination);
        verify(brandPersistencePort).getAllBrands(page, size, sortOrder);
    }
}
