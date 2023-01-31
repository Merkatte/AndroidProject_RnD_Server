package com.example.testproject.domain.user.service;

import com.example.testproject.domain.user.dto.LoginResponseDTO;
import com.example.testproject.domain.user.dto.SimpleUserDataDTO;
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

import java.time.LocalDateTime;
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

    public LoginResponseDTO signin(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            var user = userRepository.findByEmail(email);
            var refreshToken = jwtTokenProvider.createToken(email, user.getAppUserRoles());
            var accessToken = refresh(refreshToken);

            return new LoginResponseDTO(
                    refreshToken,
                    accessToken,
                    user.getId(),
                    user.getUsername(),
                    LocalDateTime.now()
            );
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    public LoginResponseDTO signup(AppUser appUser) {
        if (userRepository.existsByUsername(appUser.getUsername())) {
            var password = appUser.getPassword(); // encode 전 password 유지 필요 (for 로그인 처리)
            appUser.setPassword(passwordEncoder.encode(password));
            userRepository.save(appUser);

            return signin(appUser.getEmail(), password); //회원가입 성공 시 바로 로그인 처리
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

    public String refresh(String refreshToken) {
        if (!tokenRepository.existsById(refreshToken)){
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        var email = jwtTokenProvider.getEmail(refreshToken);
        var user = userRepository.findByEmail(email).getAppUserRoles();

        return jwtTokenProvider.createToken(email, user);
    }

    public Map<String, Boolean> usernameIsExist(String username){
        Map<String, Boolean> response = new HashMap<>();
        if (userRepository.existsByUsername(username)){
            response.put("you-can-use", true);
            return response;
        }
        response.put("you-can-use", false);
        return response;
    }

    public SimpleUserDataDTO getSimpleUserDataByEmail(String email){
        var user = userRepository.findByEmail(email);
        return new SimpleUserDataDTO(user.getId(), user.getUsername());
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
