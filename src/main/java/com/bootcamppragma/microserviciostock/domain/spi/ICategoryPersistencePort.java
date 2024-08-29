package com.bootcamppragma.microserviciostock.domain.spi;

import com.bootcamppragma.microserviciostock.domain.model.Category;

public interface ICategoryPersistencePort {

    void saveCategory(Category category);

    Category getCategory(String name);
}
