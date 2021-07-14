package ru.geekbrains.lesson07springbootspringdata.controller;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
