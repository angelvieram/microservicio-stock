package com.bootcamppragma.microserviciostock.domain.util;

public final class DomainConstants {

    private DomainConstants() {throw new IllegalStateException("Utility class");}

    public enum Field {
    NAME,
    DESCRIPTION
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name'canot be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description'canot be null";
    public static final String FIELD_PRICE_NULL_MESSAGE = "Field 'price'canot be null";
    public static final String FIELD_ARTICLE_MIN_MAX_CATEGORIES_RESTRICTION_MESSAGE = "Article must have between 1 and 3 categories";
    public static final String FIELD_ARTICLE_UNIQUE_CATEGORY_RESTRICTION_MESSAGE = "Categories must be unique";
    public static final String FIELD_PRICE_GREATER_ZERO_RESTRICTION_MESSAGE = "Price must be greater than zero";
    public static final String FIELD_QUANTITY_GREATER_ZERO_RESTRICTION_MESSAGE = "Quantity must be greater than 0";
    public static final int MAX_CATEGORY_NAME_LENGTH = 50;
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;
    public static final int MAX_BRAND_NAME_LENGTH = 50;
    public static final int MAX_BRAND_DESCRIPTION_LENGTH = 120;
}
