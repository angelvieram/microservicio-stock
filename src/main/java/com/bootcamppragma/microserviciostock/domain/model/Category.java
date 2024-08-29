package com.bootcamppragma.microserviciostock.domain.model;

import com.bootcamppragma.microserviciostock.domain.exception.EmptyFieldException;
import com.bootcamppragma.microserviciostock.domain.exception.InvalidCategoryLengthException;
import com.bootcamppragma.microserviciostock.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Category {

    private final Long id;

    private final String name;

    private final String description;

    // Constructor sin validaciones
    public Category(Long id, String name, String description) {
        if (name.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if (description.trim().isEmpty()){
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if (name.length() > DomainConstants.MAX_CATEGORY_NAME_LENGTH){
            throw new InvalidCategoryLengthException(DomainConstants.Field.NAME.toString());
        }

        if (description.length() > DomainConstants.MAX_CATEGORY_DESCRIPTION_LENGTH){
            throw new InvalidCategoryLengthException(DomainConstants.Field.DESCRIPTION.toString());
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
