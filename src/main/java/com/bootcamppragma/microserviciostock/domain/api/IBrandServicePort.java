package com.bootcamppragma.microserviciostock.domain.api;

import com.bootcamppragma.microserviciostock.domain.model.Brand;

import java.util.List;

public interface IBrandServicePort {

    void saveBrand(Brand brand);

    Brand getBrand(String name);

    List<Brand> getAllBrands(Integer page, Integer size, String sortOrder);
}
