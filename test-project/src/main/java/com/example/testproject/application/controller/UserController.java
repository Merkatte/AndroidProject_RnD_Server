package com.example.testproject.application.controller;

import com.example.testproject.domain.user.dto.UserLoginDTO;
import com.example.testproject.domain.user.entity.AppUser;
import com.example.testproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody AppUser user){
        System.out.println("is it work?(controller)");

        return userService.signup(user);
    }

    @PostMapping("/signin")
    public Map<String, String> login(@RequestBody UserLoginDTO params) {
        return userService.signin(params.email(), params.password());
    }


}