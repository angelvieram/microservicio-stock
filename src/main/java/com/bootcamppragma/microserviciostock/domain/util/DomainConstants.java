package com.bootcamppragma.microserviciostock.domain.util;

public final class DomainConstants {

    private DomainConstants() {throw new IllegalStateException("Utility class");}

    public enum Field {
    NAME,
    DESCRIPTION
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name'canot be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description'canot be null";
    public static final int MAX_CATEGORY_NAME_LENGTH = 50;
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;
}
