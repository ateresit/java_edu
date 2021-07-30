package ru.geekbrains.springsecurity.persist;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class ProductSpecifications {

    public static Specification<Product> productPrefix(String prefix) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), prefix + "%");
    }

    public static Specification<Product> minCost(BigDecimal minCost) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.ge(root.get("cost"), minCost);
    }

    public static Specification<Product> maxCost(BigDecimal maxCost) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.le(root.get("cost"), maxCost);
    }

}
