package com.bootcamppragma.microserviciostock.domain.exception;

public class InvalidBrandLengthException extends RuntimeException{
    public InvalidBrandLengthException(String message) {
        super(message);
    }
}
