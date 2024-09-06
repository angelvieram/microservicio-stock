package com.bootcamppragma.microserviciostock.domain.api;

import com.bootcamppragma.microserviciostock.domain.model.Category;
import com.bootcamppragma.microserviciostock.domain.util.Pagination;

public interface ICategoryServicePort {

    void saveCategory(Category category);

    Category getCategory(String name);

    Pagination<Category> getAllCategories(Integer page, Integer size, String sortOrder);
}
