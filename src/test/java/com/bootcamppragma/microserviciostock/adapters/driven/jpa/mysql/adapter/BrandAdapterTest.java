package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.adapter;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.BrandAlreadyExistsException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
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

class BrandAdapterTest {

    private IBrandRepository brandRepository;
    private IBrandEntityMapper brandEntityMapper;
    private BrandAdapter brandAdapter;

    @BeforeEach
    void setUp() {
        brandRepository = Mockito.mock(IBrandRepository.class);
        brandEntityMapper = Mockito.mock(IBrandEntityMapper.class);
        brandAdapter = new BrandAdapter(brandRepository, brandEntityMapper);
    }

    @Test
    @DisplayName("Should save brand successfully when it doesn't already exist")
    void givenValidBrand_whenSaveBrand_thenBrandIsSaved() {
        // Given
        Brand brand = new Brand(1L, "Nike", "Sports brand");
        BrandEntity brandEntity = new BrandEntity();
        when(brandRepository.findByName(brand.getName())).thenReturn(Optional.empty());
        when(brandEntityMapper.toEntity(brand)).thenReturn(brandEntity);

        // When
        brandAdapter.saveBrand(brand);

        // Then
        verify(brandRepository).save(brandEntity);
    }

    @Test
    @DisplayName("Should throw BrandAlreadyExistsException when the brand already exists")
    void givenExistingBrand_whenSaveBrand_thenThrowBrandAlreadyExistsException() {
        // Given
        Brand brand = new Brand(1L, "Nike", "Sports brand");
        when(brandRepository.findByName(brand.getName())).thenReturn(Optional.of(new BrandEntity()));

        // When / Then
        assertThrows(BrandAlreadyExistsException.class, () -> brandAdapter.saveBrand(brand));
        verify(brandRepository, never()).save(any(BrandEntity.class));
    }

    @Test
    @DisplayName("Should return the correct brand when given a name")
    void givenBrandName_whenGetBrand_thenReturnBrand() {
        // Given
        String brandName = "Nike";
        BrandEntity brandEntity = new BrandEntity();
        Brand expectedBrand = new Brand(1L, brandName, "Sports brand");
        when(brandRepository.findByNameContaining(brandName)).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toModel(brandEntity)).thenReturn(expectedBrand);

        // When
        Brand actualBrand = brandAdapter.getBrand(brandName);

        // Then
        assertEquals(expectedBrand, actualBrand);
        verify(brandRepository).findByNameContaining(brandName);
    }

    @Test
    @DisplayName("Should throw ElementNotFoundException when brand is not found by name")
    void givenNonExistingBrandName_whenGetBrand_thenThrowElementNotFoundException() {
        // Given
        String brandName = "UnknownBrand";
        when(brandRepository.findByNameContaining(brandName)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ElementNotFoundException.class, () -> brandAdapter.getBrand(brandName));
        verify(brandRepository).findByNameContaining(brandName);
    }

    @Test
    @DisplayName("Should return paginated brands when pagination parameters are provided")
    void givenPaginationParams_whenGetAllBrands_thenReturnPaginatedBrands() {
        // Given
        Integer page = 0;
        Integer size = 10;
        String sortOrder = "asc";
        BrandEntity brandEntity = new BrandEntity();
        Brand brand = new Brand(1L, "Nike", "Sports brand");
        Page<BrandEntity> brandEntityPage = new PageImpl<>(List.of(brandEntity));
        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandEntityPage);
        when(brandEntityMapper.toModel(brandEntity)).thenReturn(brand);

        // When
        Pagination<Brand> pagination = brandAdapter.getAllBrands(page, size, sortOrder);

        // Then
        assertNotNull(pagination);
        assertEquals(1, pagination.getContent().size());
        assertEquals(brand, pagination.getContent().get(0));
        verify(brandRepository).findAll(any(Pageable.class));
    }
}