package com.bootcamppragma.microserviciostock.domain.api.usecase;

import com.bootcamppragma.microserviciostock.domain.api.ICategoryServicePort;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.bootcamppragma.microserviciostock.domain.spi.ICategoryPersistencePort;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;

public class CategoryUseCase implements ICategoryServicePort {

    private ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public Category getCategory(String name) {
        return categoryPersistencePort.getCategory(name);
    }

    @Override
    public Pagination<Category> getAllCategories(Integer page, Integer size, String sortOrder) {
        return categoryPersistencePort.getAllCategories(page, size, sortOrder);
    }
}