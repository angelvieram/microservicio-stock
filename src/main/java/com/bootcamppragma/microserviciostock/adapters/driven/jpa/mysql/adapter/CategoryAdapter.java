package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.adapter;


import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.bootcamppragma.microserviciostock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

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
    public List<Category> getAllCategories(Integer page, Integer size, String sortOrder) {
        Pageable pagination = PageRequest.of(page, size, Sort.by("name").ascending());

        if ("desc".equalsIgnoreCase(sortOrder)) {
            pagination = PageRequest.of(page, size, Sort.by("name").descending());
        }
        List<CategoryEntity> categories = categoryRepository.findAll(pagination).getContent();
        if (categories.isEmpty()) {
            throw new NoDataFoundException();
        }
        return categoryEntityMapper.toModelList(categories);
    }
}
