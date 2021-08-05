package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.controller.UserDto;
import ru.geekbrains.controller.UserListParams;
import ru.geekbrains.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping(path = "/filter", produces = "application/json")
    public Page<UserDto> findWithFilter(UserListParams userListParams) {
        return userService.findWithFilter(userListParams);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public UserDto findById(@PathVariable("id") Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(produces = "application/json")
    public UserDto create(@RequestBody UserDto user) {
        if (user.getId() != null) {
            throw new BadRequestException("User Id should be null");
        }
        userService.save(user);
        return user;
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(produces = "application/json")
    public void update(@RequestBody UserDto user) {
        if (user.getId() == null) {
            throw new BadRequestException("User Id shouldn't be null");
        }
        userService.save(user);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }
}
