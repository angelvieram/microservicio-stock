package com.bootcamppragma.microserviciostock.domain.model;

import com.bootcamppragma.microserviciostock.domain.exception.EmptyFieldException;
import com.bootcamppragma.microserviciostock.domain.exception.InvalidArticleCategoryException;
import com.bootcamppragma.microserviciostock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class Article {
    private final Long id;
    private final String name;
    private final String description;
    private final Integer quantity;
    private final BigDecimal price;
    private final Set<Category> categories;

    public Article(Long id, String name, String description, Integer quantity, BigDecimal price, List<Category> categories) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if (description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(DomainConstants.FIELD_QUANTITY_GREATER_ZERO_RESTRICTION_MESSAGE);
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(DomainConstants.FIELD_PRICE_GREATER_ZERO_RESTRICTION_MESSAGE);
        }

        if (categories == null || categories.isEmpty() || categories.size() > 3) {
            throw new InvalidArticleCategoryException(DomainConstants.FIELD_ARTICLE_MIN_MAX_CATEGORIES_RESTRICTION_MESSAGE);
        }

        // Validate unique categories
        Set<Category> uniqueCategories = new HashSet<>(categories);
        if (uniqueCategories.size() != categories.size()) {
            throw new InvalidArticleCategoryException(DomainConstants.FIELD_ARTICLE_UNIQUE_CATEGORY_RESTRICTION_MESSAGE);
        }

        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        this.quantity = quantity;
        this.price = requireNonNull(price, DomainConstants.FIELD_PRICE_NULL_MESSAGE);
        this.categories = new HashSet<>(categories); // Ensure immutability
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

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Set<Category> getCategories() {
        return new HashSet<>(categories); // Return a copy to maintain immutability
    }
}