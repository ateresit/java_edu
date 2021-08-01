package ru.geekbrains.springsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.springsecurity.persist.User;
import ru.geekbrains.springsecurity.persist.UserRepository;
import ru.geekbrains.springsecurity.service.UserService;

import javax.validation.Valid;
import java.util.Properties;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listPage(Model model) {
        logger.info("User list page requested");

        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        logger.info("New user page requested");

        model.addAttribute("user", new User());
        return "user_form";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        logger.info("Edit user page requested");

        model.addAttribute("user", userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        return "user_form";
    }

    @PostMapping
    public String update(@Valid UserDto user, BindingResult result) {
        logger.info("Saving user");

        if (result.hasErrors()) {
            return "user_form";
        }

//        if (user.getAge() > 25) {
//            result.rejectValue("age", "", "Error message");
//            return "user_form";
//        }

        userService.save(user);
        return "redirect:/user";

    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        logger.info("Deleting user with id {}", id);

        userService.deleteById(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView =  new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
