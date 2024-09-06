package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.adapter;


import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.bootcamppragma.microserviciostock.domain.spi.ICategoryPersistencePort;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException();
        }
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public Category getCategory(String name) {
        CategoryEntity category = categoryRepository.findByNameContaining(name).orElseThrow(ElementNotFoundException::new);
        return categoryEntityMapper.toModel(category);
    }

    @Override
    public Pagination<Category> getAllCategories(Integer page, Integer size, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageable);

        List<Category> categories = categoryPage.getContent().stream()
                .map(categoryEntityMapper::toModel)
                .collect(Collectors.toList());

        return new Pagination<>(
                categories,
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages()
        );
    }
}
