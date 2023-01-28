package com.example.testproject.domain.user.service;

import com.example.testproject.domain.user.entity.AppUser;
import com.example.testproject.domain.user.exception.CustomException;
import com.example.testproject.domain.user.repository.TokenRepository;
import com.example.testproject.domain.user.repository.UserRepository;
import com.example.testproject.domain.user.security.JwtTokenProvider;
import com.example.testproject.domain.user.security.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RedisUtil redisUtil;
    private final TokenRepository tokenRepository;

    public Map<String, String> signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email, userRepository.findByEmail(email).getAppUserRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Map<String, String> signup(AppUser appUser) {
        if (!userRepository.existsByUsername(appUser.getUsername())) {
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            userRepository.save(appUser);

            return jwtTokenProvider.createToken(appUser.getUsername(), appUser.getAppUserRoles());
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String logout(String refreshToken, String accessToken){

        jwtTokenProvider.validateToken(accessToken);
        tokenRepository.deleteById(refreshToken);
        redisUtil.setBlackList(accessToken, "accessToken", jwtTokenProvider.getAccessTokenExpiration());
        return "logged out successfully";
    }

    public Map<String, String> refresh(String refreshToken) {
        if (!tokenRepository.existsById(refreshToken)){
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Map<String, String> accessToken = new HashMap<>();
        accessToken.put("accessToken", jwtTokenProvider.createAccessToken(refreshToken));
        return accessToken;
    }

    public Map<String, Boolean> usernameIsExist(String username){
        Map<String, Boolean> response = new HashMap<>();
        if (!userRepository.existsByUsername(username)){
            response.put("you-can-use", true);
            return response;
        }
        response.put("you-can-use", false);
        return response;
    }

    /*

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
 */
}
