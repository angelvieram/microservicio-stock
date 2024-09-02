package com.bootcamppragma.microserviciostock.adapters.driven.http.controller;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddBrandRequest;
import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.BrandResponse;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.IBrandRequestMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.IBrandResponseMapper;
import com.bootcamppragma.microserviciostock.domain.api.IBrandServicePort;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BrandRestControllerAdapterTest {

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;

    @Mock
    private IBrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandRestControllerAdapter brandRestControllerAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Dado un request debe crear la marca y devolver un 201")
    void addBrand() {
        // GIVEN
        AddBrandRequest request = new AddBrandRequest("Sony", "Electronics and gadgets");
        Brand brand = new Brand(1L, request.getName(), request.getDescription());

        when(brandRequestMapper.addRequestToBrand(request)).thenReturn(brand);

        // WHEN
        ResponseEntity<Void> response = brandRestControllerAdapter.addBrand(request);

        // THEN
        verify(brandServicePort, times(1)).saveBrand(brand);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Dado el nombre de una marca debe devolver el BrandResponse correspondiente")
    void getBrand() {
        // GIVEN
        String brandName = "Sony";
        Brand brand = new Brand(1L, brandName, "Electronics and gadgets");
        BrandResponse expectedResponse = new BrandResponse(1L, brandName, "Electronics and gadgets");

        when(brandServicePort.getBrand(brandName)).thenReturn(brand);
        when(brandResponseMapper.toBrandResponse(brand)).thenReturn(expectedResponse);

        // WHEN
        ResponseEntity<BrandResponse> response = brandRestControllerAdapter.getBrand(brandName);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(brandServicePort, times(1)).getBrand(brandName);
        verify(brandResponseMapper, times(1)).toBrandResponse(brand);
    }

    //hu4
    @Test
    @DisplayName("Dadas algunas marcas y parametros debe devolver la lista")
    void getAllBrands() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortOrder = "asc";

        Brand brand1 = new Brand(1L, "Brand 1", "Description 1");
        Brand brand2 = new Brand(2L, "Brand 2", "Description 2");
        List<Brand> brands = Arrays.asList(brand1, brand2);

        BrandResponse brandResponse1 = new BrandResponse(1L, "Brand 1", "Description 1");
        BrandResponse brandResponse2 = new BrandResponse(2L, "Brand 2", "Description 2");
        List<BrandResponse> brandResponses = Arrays.asList(brandResponse1, brandResponse2);

        when(brandServicePort.getAllBrands(page, size, sortOrder)).thenReturn(brands);
        when(brandResponseMapper.toBrandResponseList(brands)).thenReturn(brandResponses);

        // Act
        ResponseEntity<List<BrandResponse>> response = brandRestControllerAdapter.getAllBrands(page, size, sortOrder);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(brandResponses, response.getBody());
        verify(brandServicePort, times(1)).getAllBrands(page, size, sortOrder);
        verify(brandResponseMapper, times(1)).toBrandResponseList(brands);
    }
}
