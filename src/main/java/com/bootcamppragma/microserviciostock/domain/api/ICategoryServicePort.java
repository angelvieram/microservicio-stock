package com.bootcamppragma.microserviciostock.domain.api;

import com.bootcamppragma.microserviciostock.domain.model.Category;



public interface    ICategoryServicePort {

    void saveCategory(Category category);

    Category getCategory(String name);
}
