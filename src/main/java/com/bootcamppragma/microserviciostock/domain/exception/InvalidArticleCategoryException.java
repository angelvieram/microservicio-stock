package com.bootcamppragma.microserviciostock.domain.exception;

public class InvalidArticleCategoryException extends RuntimeException {
    public InvalidArticleCategoryException(String message) {
        super(message);
    }
}
