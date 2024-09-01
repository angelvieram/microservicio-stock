package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.adapter;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.BrandAlreadyExistsException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
import com.bootcamppragma.microserviciostock.domain.spi.IBrandPersistencePort;

import lombok.RequiredArgsConstructor;

//hu3
@RequiredArgsConstructor
public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public void saveBrand(Brand brand) {
        if (brandRepository.findByName(brand.getName()).isPresent()) {
            throw new BrandAlreadyExistsException();
        }
        brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    public Brand getBrand(String name) {
        BrandEntity brand = brandRepository.findByNameContaining(name).orElseThrow(ElementNotFoundException::new);
        return brandEntityMapper.toModel(brand);
    }



}
