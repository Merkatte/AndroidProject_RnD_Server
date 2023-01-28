package com.example.testproject.application.controller;

import com.example.testproject.domain.user.dto.LogoutDTO;
import com.example.testproject.domain.user.dto.UserLoginDTO;
import com.example.testproject.domain.user.entity.AppUser;
import com.example.testproject.domain.user.security.JwtTokenProvider;
import com.example.testproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;
    final private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody AppUser user){
        return userService.signup(user);
    }

    @PostMapping("/signin")
    public Map<String, String> signin(@RequestBody UserLoginDTO params) {
        return userService.signin(params.email(), params.password());
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req, @RequestBody LogoutDTO refreshToken){
        System.out.println(refreshToken);
        return userService.logout(refreshToken.refreshToken(), jwtTokenProvider.resolveToken(req));
    }

    @GetMapping("/refresh")
    public Map<String, String> refresh(HttpServletRequest req){
        return userService.refresh(jwtTokenProvider.resolveToken(req));
    }

    @GetMapping("/username-is-exist")
    public Map<String, Boolean> usernameIsExist(@RequestParam String username){
        return userService.usernameIsExist(username);
    }

}