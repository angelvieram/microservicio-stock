package com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.mapper;

import com.bootcamppragma.microserviciostock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamppragma.microserviciostock.domain.model.Brand;
import org.mapstruct.Mapper;

//hu3
@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {

    Brand toModel(BrandEntity brandEntity);

    BrandEntity toEntity(Brand brand);
}
