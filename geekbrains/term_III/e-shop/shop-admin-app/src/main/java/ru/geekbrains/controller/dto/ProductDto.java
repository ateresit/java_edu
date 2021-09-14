package ru.geekbrains.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {
    private Long id;

    private String title;

    private String description;

    private BigDecimal cost;

    private CategoryDto category;

    private List<Integer> pictures;

    private MultipartFile[] newPictures;

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

    public List<Integer> getPictures() {
        return pictures;
    }

    public void setPictures(List<Integer> pictures) {
        this.pictures = pictures;
    }

    public MultipartFile[] getNewPictures() {
        return newPictures;
    }

    public void setNewPictures(MultipartFile[] newPictures) {
        this.newPictures = newPictures;
    }
}
