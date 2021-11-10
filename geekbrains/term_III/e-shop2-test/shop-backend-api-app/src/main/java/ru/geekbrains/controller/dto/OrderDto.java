package ru.geekbrains.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    private Long id;

    private LocalDateTime orderDate;

    private String status;

    private String username;

    private List<OrderLineItemDto> orderLineItems;

    public OrderDto() {
    }

    public OrderDto(Long id, LocalDateTime orderDate, String status, String username, List<OrderLineItemDto> orderLineItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.username = username;
        this.orderLineItems = orderLineItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItemDto> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }
}
