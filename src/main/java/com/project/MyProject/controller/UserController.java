package com.project.MyProject.controller;

import com.project.MyProject.dto.UserDto;
import com.project.MyProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String createUser(@RequestBody final UserDto userDto) {
        userService.registerUser(userDto);
        return "Successfully registered";
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getUsersList();
    }
}
