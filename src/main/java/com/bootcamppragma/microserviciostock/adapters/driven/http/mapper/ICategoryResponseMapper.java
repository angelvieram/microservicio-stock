package com.bootcamppragma.microserviciostock.adapters.driven.http.mapper;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.CategoryResponse;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {

    CategoryResponse toCategoryResponse(Category category);
}
