package com.bootcamppragma.microserviciostock.configuration;


// (2:07:09) Van a ser usadas en la capa de adaptadores, pero puede tenerse aca ya que esta muy general para los primarios como los secundarios.
public class Constants {
    private Constants(){throw new IllegalStateException("Utility class");}

    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The category you want to create already exists";
}
