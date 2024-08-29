package com.bootcamppragma.microserviciostock.domain.exception;

public class InvalidCategoryLengthException extends RuntimeException {
    public InvalidCategoryLengthException(String message) {
        super(message);
    }
}
