package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryEntityMapper {

    Category toModel(CategoryEntity categoryEntity);

    CategoryEntity toEntity(Category category);

    List<Category> toModelList(List<CategoryEntity> productEntities);
}
