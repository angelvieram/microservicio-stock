package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.adapter;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.BrandAlreadyExistsException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
import com.bootcamppragma.microserviciostock.domain.spi.IBrandPersistencePort;

import com.bootcamppragma.microserviciostock.domain.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

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

    //hu4
    @Override
    public Pagination<Brand> getAllBrands(Integer page, Integer size, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BrandEntity> brandPage = brandRepository.findAll(pageable);

        List<Brand> brands = brandPage.getContent().stream()
                .map(brandEntityMapper::toModel)
                .collect(Collectors.toList());

        return new Pagination<>(
                brands,
                brandPage.getNumber(),
                brandPage.getSize(),
                brandPage.getTotalElements(),
                brandPage.getTotalPages()
        );
    }
}
