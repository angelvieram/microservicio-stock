package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByNameContaining(String name);

    Optional<CategoryEntity> findByName(String name);

}
