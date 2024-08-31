package com.bootcamppragma.microserviciostock.domain.api;

import com.bootcamppragma.microserviciostock.domain.model.Category;

import java.util.List;


public interface ICategoryServicePort {

    void saveCategory(Category category);

    Category getCategory(String name);

    List<Category> getAllCategories(Integer page, Integer size, String sortOrder);
}
