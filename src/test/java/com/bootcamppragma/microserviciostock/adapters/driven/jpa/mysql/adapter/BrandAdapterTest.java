package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.adapter;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.BrandAlreadyExistsException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private IBrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandAdapter brandAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería guardar una marca si no existe")
    void saveBrand() {
        // GIVEN
        Brand brand = new Brand(1L, "Nike", "Sportswear brand");
        BrandEntity brandEntity = new BrandEntity(1L, "Nike", "Sportswear brand");

        when(brandRepository.findByName(brand.getName())).thenReturn(Optional.empty());
        when(brandEntityMapper.toEntity(brand)).thenReturn(brandEntity);

        // WHEN
        brandAdapter.saveBrand(brand);

        // THEN
        verify(brandRepository, times(1)).save(brandEntity);
    }

    @Test
    @DisplayName("Debería lanzar BrandAlreadyExistsException si la marca ya existe")
    void saveBrand_shouldThrowException_whenBrandExists() {
        // GIVEN
        Brand brand = new Brand(1L, "Nike", "Sportswear brand");
        when(brandRepository.findByName(brand.getName())).thenReturn(Optional.of(new BrandEntity()));

        // WHEN & THEN
        assertThrows(BrandAlreadyExistsException.class, () -> brandAdapter.saveBrand(brand));
    }

    @Test
    @DisplayName("Debería obtener una marca por su nombre")
    void getBrand() {
        // GIVEN
        String name = "Nike";
        BrandEntity brandEntity = new BrandEntity(1L, name, "Sportswear brand");
        Brand brand = new Brand(1L, name, "Sportswear brand");

        when(brandRepository.findByNameContaining(name)).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toModel(brandEntity)).thenReturn(brand);

        // WHEN
        Brand result = brandAdapter.getBrand(name);

        // THEN
        assertEquals(brand, result);
    }

    @Test
    @DisplayName("Debería lanzar ElementNotFoundException si la marca no existe")
    void getBrand_shouldThrowException_whenBrandNotFound() {
        // GIVEN
        String name = "NonExistentBrand";
        when(brandRepository.findByNameContaining(name)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ElementNotFoundException.class, () -> brandAdapter.getBrand(name));
    }
}
