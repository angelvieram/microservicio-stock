package com.bootcamppragma.microserviciostock.adapters.driven.http.mapper;


import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddCategoryRequest;
import com.bootcamppragma.microserviciostock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    Category addRequestToCategory(AddCategoryRequest addCategoryRequest);


}
