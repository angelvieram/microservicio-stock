package com.bootcamppragma.microserviciostock.adapters.driven.http.mapper;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request.AddBrandRequest;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//hu3
@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {

    @Mapping(target = "id", ignore = true)
    Brand addRequestToBrand(AddBrandRequest addBrandRequest);
}
