package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping()
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}/id")
    public User findById(@PathVariable("id") long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id = " + id + " not exists"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("Can't create user with id not null");
        }
        return userRepository.save(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("Can't update user with id null");
        }
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        userRepository.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundExceptionHandler(NotFoundException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto notFoundExceptionHandler(IllegalArgumentException ex) {
        return new ErrorDto(ex.getMessage());
    }

}
