package ru.geekbrains.springsecurity.controller;

import java.math.BigDecimal;

public class ProductListParams {

    private String productFilter;
    private BigDecimal minCost;
    private BigDecimal maxCost;
    private Integer page;
    private Integer size;
    private String sortField;

    public String getProductFilter() {
        return productFilter;
    }

    public void setProductFilter(String productFilter) {
        this.productFilter = productFilter;
    }

    public BigDecimal getMinCost() {
        return minCost;
    }

    public void setMinCost(BigDecimal minCost) {
        this.minCost = minCost;
    }

    public BigDecimal getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(BigDecimal maxCost) {
        this.maxCost = maxCost;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
}
