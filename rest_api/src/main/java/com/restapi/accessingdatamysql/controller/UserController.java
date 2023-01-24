package com.restapi.accessingdatamysql.controller;

import com.restapi.accessingdatamysql.domain.User;
import com.restapi.accessingdatamysql.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/account")
public class UserController {

    private UserRepository userRepository;

    @PostMapping(path = "/create")
    public @ResponseBody String addNewUser(@RequestParam String username, @RequestParam String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        userRepository.save(user);
        return "new user created";
    }

    @GetMapping(path = "/get")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
