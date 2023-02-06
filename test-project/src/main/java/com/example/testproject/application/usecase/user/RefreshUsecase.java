package com.example.testproject.application.usecase.user;

import com.example.testproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RefreshUsecase {
    final private UserService userService;

    public Map<String, String> execute(String refreshToken){
        Map<String, String> response = new HashMap<>();
        var accessToken = userService.refresh(refreshToken);
        response.put("accessToken", accessToken);
        return response;
    }

}
