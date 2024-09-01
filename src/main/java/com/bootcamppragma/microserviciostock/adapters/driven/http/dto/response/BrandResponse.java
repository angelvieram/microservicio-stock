package com.bootcamppragma.microserviciostock.adapters.driven.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

//hu3
@AllArgsConstructor
@Getter
public class BrandResponse {
    private final Long id;

    private final String name;

    private final String description;
}
