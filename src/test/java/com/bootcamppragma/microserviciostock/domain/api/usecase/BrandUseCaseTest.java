package com.bootcamppragma.microserviciostock.domain.api.usecase;

import com.bootcamppragma.microserviciostock.domain.model.Brand;
import com.bootcamppragma.microserviciostock.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @Test
    @DisplayName("Dada una marca debe guardarla correctamente en la bd")
    void saveBrand() {
        // GIVEN
        Brand brand = new Brand(1L, "Sony", "Electronics and gadgets");

        // WHEN
        brandUseCase.saveBrand(brand);

        // THEN
        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }

    @Test
    @DisplayName("Dado el nombre de una marca debe devolver la marca correspondiente")
    void getBrand() {
        // GIVEN
        String brandName = "Sony";
        Brand expectedBrand = new Brand(1L, brandName, "Electronics and gadgets");
        when(brandPersistencePort.getBrand(brandName)).thenReturn(expectedBrand);

        // WHEN
        Brand actualBrand = brandUseCase.getBrand(brandName);

        // THEN
        assertNotNull(actualBrand);
        assertEquals(expectedBrand, actualBrand);
        verify(brandPersistencePort, times(1)).getBrand(brandName);
    }
}
