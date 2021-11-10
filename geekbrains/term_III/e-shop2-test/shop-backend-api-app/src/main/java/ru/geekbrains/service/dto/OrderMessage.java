package ru.geekbrains.service.dto;

public class OrderMessage {

    private Long id;

    private String state;

    public OrderMessage() {
    }

    public OrderMessage(Long id, String state) {
        this.id = id;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
