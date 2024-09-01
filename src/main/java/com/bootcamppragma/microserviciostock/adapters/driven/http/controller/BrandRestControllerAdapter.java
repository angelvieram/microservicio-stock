package com.bootcamppragma.microserviciostock.adapters.driven.http.controller;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddBrandRequest;
import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.BrandResponse;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.IBrandRequestMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.http.mapper.IBrandResponseMapper;
import com.bootcamppragma.microserviciostock.domain.api.IBrandServicePort;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//hu3
@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {

    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;

    @Operation(summary = "create brand")
    @PostMapping("/")
    public ResponseEntity<Void> addBrand(@RequestBody AddBrandRequest request) {
        brandServicePort.saveBrand(brandRequestMapper.addRequestToBrand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Search brand by name")
    @GetMapping("/{brandName}")
    public ResponseEntity<BrandResponse> getBrand(@PathVariable String brandName) {
        return ResponseEntity.ok(brandResponseMapper.toBrandResponse(brandServicePort.getBrand(brandName)));
    }
    //hu4
}
