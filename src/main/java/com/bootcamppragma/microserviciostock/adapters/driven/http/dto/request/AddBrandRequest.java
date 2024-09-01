package com.bootcamppragma.microserviciostock.adapters.driven.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

//hu3
@AllArgsConstructor
@Getter
public class AddBrandRequest {

    private final String name;

    private final String description;
}
