package ru.geekbrains.rest;

import java.time.LocalDateTime;

public class ErrorDto {

    private LocalDateTime timeStamp;

    private String message;

    public ErrorDto() {
    }

    public ErrorDto(String message) {
        this.timeStamp = LocalDateTime.now();
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
