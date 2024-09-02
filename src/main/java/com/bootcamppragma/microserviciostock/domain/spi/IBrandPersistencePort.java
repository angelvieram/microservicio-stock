package com.bootcamppragma.microserviciostock.domain.spi;

import com.bootcamppragma.microserviciostock.domain.model.Brand;

import java.util.List;


public interface IBrandPersistencePort {

    void saveBrand(Brand brand);

    Brand getBrand(String name);

    List<Brand> getAllBrands(Integer page, Integer size, String sortOrder);
}
