package com.bootcamppragma.microserviciostock.domain.api;

import com.bootcamppragma.microserviciostock.domain.model.Brand;

public interface IBrandServicePort {

    void saveBrand(Brand brand);

    Brand getBrand(String name);
}
