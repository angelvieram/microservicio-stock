package com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCategoryRequest {

    private final String name;

    private final String description;
}
