package com.bootcamppragma.microserviciostock.adapters.driven.http.controller;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddBrandRequest;
import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.BrandResponse;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.IBrandRequestMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.IBrandResponseMapper;
import com.bootcamppragma.microserviciostock.domain.api.IBrandServicePort;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrandRestControllerAdapterTest {

    private IBrandServicePort brandServicePort;
    private IBrandRequestMapper brandRequestMapper;
    private IBrandResponseMapper brandResponseMapper;
    private BrandRestControllerAdapter brandRestControllerAdapter;

    @BeforeEach
    void setUp() {
        brandServicePort = Mockito.mock(IBrandServicePort.class);
        brandRequestMapper = Mockito.mock(IBrandRequestMapper.class);
        brandResponseMapper = Mockito.mock(IBrandResponseMapper.class);
        brandRestControllerAdapter = new BrandRestControllerAdapter(brandServicePort, brandRequestMapper, brandResponseMapper);
    }

    @Test
    @DisplayName("Should create brand successfully")
    void givenValidBrandRequest_whenAddBrand_thenReturnCreatedStatus() {
        // Given
        AddBrandRequest request = new AddBrandRequest("Nike", "Sports brand");
        Brand brand = new Brand(1L, "Nike", "Sports brand");
        when(brandRequestMapper.addRequestToBrand(request)).thenReturn(brand);

        // When
        ResponseEntity<Void> response = brandRestControllerAdapter.addBrand(request);

        // Then
        verify(brandServicePort).saveBrand(brand);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return brand by name successfully")
    void givenBrandName_whenGetBrand_thenReturnBrandResponse() {
        // Given
        String brandName = "Nike";
        Brand brand = new Brand(1L, brandName, "Sports brand");
        BrandResponse expectedResponse = new BrandResponse(1L, brandName, "Sports brand");

        when(brandServicePort.getBrand(brandName)).thenReturn(brand);
        when(brandResponseMapper.toBrandResponse(brand)).thenReturn(expectedResponse);

        // When
        ResponseEntity<BrandResponse> response = brandRestControllerAdapter.getBrand(brandName);

        // Then
        verify(brandServicePort).getBrand(brandName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @DisplayName("Should return paginated brands successfully")
    void givenPaginationParams_whenGetAllBrands_thenReturnPaginatedBrands() {
        // Given
        Integer page = 0;
        Integer size = 10;
        String sortOrder = "asc";

        Brand brand = new Brand(1L, "Nike", "Sports brand");
        BrandResponse brandResponse = new BrandResponse(1L, "Nike", "Sports brand");

        Pagination<Brand> brandPagination = new Pagination<>(
                List.of(brand), page, size, 1L, 1
        );

        when(brandServicePort.getAllBrands(page, size, sortOrder)).thenReturn(brandPagination);
        when(brandResponseMapper.toBrandResponse(brand)).thenReturn(brandResponse);

        // When
        ResponseEntity<Pagination<BrandResponse>> response = brandRestControllerAdapter.getAllBrands(page, size, sortOrder);

        // Then
        verify(brandServicePort).getAllBrands(page, size, sortOrder);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(brandResponse, response.getBody().getContent().get(0));
    }
}