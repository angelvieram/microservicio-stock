package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByNameContaining(String name);

    Optional<BrandEntity> findByName(String name);

    Page<BrandEntity> findAll(Pageable pageable);
}
