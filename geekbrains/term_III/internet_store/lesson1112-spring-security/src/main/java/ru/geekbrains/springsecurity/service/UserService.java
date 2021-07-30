package ru.geekbrains.springsecurity.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.springsecurity.controller.UserDto;
import ru.geekbrains.springsecurity.controller.UserListParams;
import ru.geekbrains.springsecurity.persist.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();

    Page<UserDto> findWithFilter(UserListParams userListParams);

    Optional<UserDto> findById(Long id);

    void save(UserDto user);

    void deleteById(Long id);
}
