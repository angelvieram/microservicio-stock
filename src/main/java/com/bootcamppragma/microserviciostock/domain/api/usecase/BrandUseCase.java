package com.bootcamppragma.microserviciostock.domain.api.usecase;

import com.bootcamppragma.microserviciostock.domain.api.IBrandServicePort;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
import com.bootcamppragma.microserviciostock.domain.spi.IBrandPersistencePort;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;

public class BrandUseCase implements IBrandServicePort {

    private IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand) {
        brandPersistencePort.saveBrand(brand);
    }

    @Override
    public Brand getBrand(String name) {
        return brandPersistencePort.getBrand(name);
    }

    //hu4
    @Override
    public Pagination<Brand> getAllBrands(Integer page, Integer size, String sortOrder) {
        return brandPersistencePort.getAllBrands(page, size, sortOrder);
    }
}
