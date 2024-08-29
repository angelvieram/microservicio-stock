package com.bootcamppragma.microserviciostock.domain.exception;

public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String message) {
        super(message);
    }
}
