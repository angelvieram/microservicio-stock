package com.bootcamppragma.microserviciostock.configuration;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamppragma.microserviciostock.domain.api.ICategoryServicePort;
import com.bootcamppragma.microserviciostock.domain.api.usecase.CategoryUseCase;
import com.bootcamppragma.microserviciostock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;

    private final ICategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }
}
