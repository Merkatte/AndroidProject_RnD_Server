package dnd.auction.application.controller.user;

import dnd.auction.application.usecase.user.FollowUsecase;
import dnd.auction.application.usecase.user.RefreshUsecase;
import dnd.auction.domain.user.dto.LoginDTO;
import dnd.auction.domain.user.dto.LogoutDTO;
import dnd.auction.domain.user.entity.Follow;
import dnd.auction.domain.user.dto.LoginResponseDTO;
import dnd.auction.domain.user.entity.AppUser;
import dnd.auction.domain.user.security.JwtTokenProvider;
import dnd.auction.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;
    final private RefreshUsecase refreshUsecase;
    final private JwtTokenProvider jwtTokenProvider;
    final private FollowUsecase followUsecase;

    @PostMapping("/signup")
    public LoginResponseDTO signup(@RequestBody AppUser user){
        return userService.signup(user);
    }

    @PostMapping("/signin")
    public LoginResponseDTO signin(@RequestBody LoginDTO params) {
        return userService.signin(params.email(), params.password());
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req, @RequestBody LogoutDTO refreshToken){
        return userService.logout(refreshToken.refreshToken(), jwtTokenProvider.resolveToken(req));
    }

    @GetMapping("/refresh")
    public Map<String, String> refresh(HttpServletRequest req){
        return refreshUsecase.execute(jwtTokenProvider.resolveToken(req));
    }

    @GetMapping("/username-is-exist")
    public Map<String, Boolean> usernameIsExist(@RequestParam String username){
        return userService.usernameIsExist(username);
    }

    @PostMapping("/follow")
    public void follow(@RequestBody Follow follow){
        followUsecase.execute(follow);
    }

}