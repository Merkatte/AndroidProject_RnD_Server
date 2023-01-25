package com.example.testproject.domain.user.service;

import com.example.testproject.domain.user.entity.AppUser;
import com.example.testproject.domain.user.exception.CustomException;
import com.example.testproject.domain.user.repository.UserRepository;
import com.example.testproject.domain.user.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public Map<String, String> signin(String email, String password) {
        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            var refreshToken = jwtTokenProvider.createRefreshToken(email, userRepository.findByEmail(email).getAppUserRoles());

            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put("refreshToken", refreshToken);
            tokenResponse.put("accessToken", jwtTokenProvider.createAccessToken(refreshToken));
            return tokenResponse;

        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(AppUser appUser) {
        if (!userRepository.existsByUsername(appUser.getUsername())) {
            System.out.println("is it work?(call signup)");
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            userRepository.save(appUser);
            System.out.println("is it work?(save)");
            return jwtTokenProvider.createRefreshToken(appUser.getUsername(), appUser.getAppUserRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Boolean existUsernameCheck(String username){
        return !userRepository.existsByUsername(username);
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public AppUser search(String username) {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return appUser;
    }

    public AppUser whoami(HttpServletRequest req) {
        return userRepository.findByEmail(jwtTokenProvider.getEmail(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String refreshToken) {
        return jwtTokenProvider.createAccessToken(refreshToken);
    }

}
