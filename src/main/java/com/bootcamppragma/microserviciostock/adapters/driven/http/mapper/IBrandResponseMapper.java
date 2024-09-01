package com.bootcamppragma.microserviciostock.adapters.driven.http.mapper;

import com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response.BrandResponse;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
import org.mapstruct.Mapper;

//hu3
@Mapper(componentModel = "spring")
public interface IBrandResponseMapper {

    BrandResponse toBrandResponse(Brand brand);
}
