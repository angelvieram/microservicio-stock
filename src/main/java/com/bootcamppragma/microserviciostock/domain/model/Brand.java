package com.bootcamppragma.microserviciostock.domain.model;

import com.bootcamppragma.microserviciostock.domain.exception.EmptyFieldException;
import com.bootcamppragma.microserviciostock.domain.exception.InvalidBrandLengthException;
import com.bootcamppragma.microserviciostock.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

//hu3
public class Brand {
    private final Long id;

    private final String name;

    private final String description;

    public Brand(Long id, String name, String description) {
        if (name.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if (description.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if (name.length() > DomainConstants.MAX_BRAND_NAME_LENGTH){
            throw new InvalidBrandLengthException(DomainConstants.Field.NAME.toString());
        }

        if (description.length() > DomainConstants.MAX_BRAND_DESCRIPTION_LENGTH){
            throw new InvalidBrandLengthException(DomainConstants.Field.DESCRIPTION.toString()) {
            };
        }

        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
