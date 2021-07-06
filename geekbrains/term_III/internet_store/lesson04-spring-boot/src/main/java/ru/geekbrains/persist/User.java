package ru.geekbrains.persist;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class User {

    private Long id;

    @NotBlank
    private String username;

    @Min(value = 18)
    private Integer age;

    public User() {
    }

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
