package ru.geekbrains.controller.dto;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;

    private String title;

    private String description;

    private BigDecimal cost;

    private CategoryDto category;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, String description, BigDecimal cost, CategoryDto category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
