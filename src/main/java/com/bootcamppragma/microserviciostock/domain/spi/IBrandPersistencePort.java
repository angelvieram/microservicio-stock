package com.bootcamppragma.microserviciostock.domain.spi;

import com.bootcamppragma.microserviciostock.domain.model.Brand;


public interface IBrandPersistencePort {

    void saveBrand(Brand brand);

    Brand getBrand(String name);
}
