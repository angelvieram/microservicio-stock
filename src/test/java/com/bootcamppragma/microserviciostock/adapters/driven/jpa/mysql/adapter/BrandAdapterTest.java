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
import org.springframework.data.domain.*;

import java.util.List;
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

    //hu4
    @Test
    @DisplayName("Should return a list of brands when brands are found")
    void testGetAllBrandsWhenBrandsAreFound() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        List<BrandEntity> brandEntities = List.of(
                new BrandEntity(1L, "Brand1", "Description1"),
                new BrandEntity(2L, "Brand2", "Description2")
        );
        Page<BrandEntity> brandEntityPage = new PageImpl<>(brandEntities, pageable, brandEntities.size());

        List<Brand> brands = List.of(
                new Brand(1L, "Brand1", "Description1"),
                new Brand(2L, "Brand2", "Description2")
        );

        when(brandRepository.findAll(pageable)).thenReturn(brandEntityPage);
        when(brandEntityMapper.toModelList(brandEntities)).thenReturn(brands);

        // Act
        List<Brand> result = brandAdapter.getAllBrands(0, 10, "asc");

        // Assert
        assertEquals(brands, result);
    }

    @Test
    @DisplayName("Should return a list of brands sorted in descending order")
    void testGetAllBrandsSortedDescending() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").descending());
        List<BrandEntity> brandEntities = List.of(
                new BrandEntity(1L, "Brand2", "Description2"),
                new BrandEntity(2L, "Brand1", "Description1")
        );
        Page<BrandEntity> brandEntityPage = new PageImpl<>(brandEntities, pageable, brandEntities.size());

        List<Brand> brands = List.of(
                new Brand(1L, "Brand2", "Description2"),
                new Brand(2L, "Brand1", "Description1")
        );

        when(brandRepository.findAll(pageable)).thenReturn(brandEntityPage);
        when(brandEntityMapper.toModelList(brandEntities)).thenReturn(brands);

        // Act
        List<Brand> result = brandAdapter.getAllBrands(0, 10, "desc");

        // Assert
        assertEquals(brands, result);
    }
}
