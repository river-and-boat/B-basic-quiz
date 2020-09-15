package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.domain.User;
import com.thoughtworks.capability.gtb.entrancequiz.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
public class UserController {

    private static final String PATTERN_ID = "/^[1-9]\\d*$/";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserInfo(@PathVariable @Validated
                            @Pattern(regexp = PATTERN_ID) Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUserInfo(@RequestBody @Valid User user) {
        return userService.saveUser(user);
    }
}
