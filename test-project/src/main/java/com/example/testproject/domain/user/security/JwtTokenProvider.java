package com.example.testproject.domain.user.security;

import com.example.testproject.domain.user.entity.AppUserRole;
import com.example.testproject.domain.user.entity.RefreshToken;
import com.example.testproject.domain.user.exception.CustomException;
import com.example.testproject.domain.user.repository.TokenRepository;
import com.example.testproject.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    /**
     * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
     * microservices' environment, this key would be kept on a config-server.
     */
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-validity-in-seconds}")
    private Long accessTokenExpiration; // 2h;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private Long refreshTokenExpiration;

    @Autowired
    private MyUserDetails myUserDetails;

    private Key key;
    final private RedisUtil redisUtil;
    final private TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public Long getAccessTokenExpiration(){
        return this.accessTokenExpiration;
    }

    @PostConstruct
    protected void init() {
        // signWith no more using String type key, must convert to Key type
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

//    public String createAccessToken(String refreshToken) {
//
//
//        Date now = new Date();
//        Date expiration = new Date(now.getTime() + accessTokenExpiration);
//
//        return Jwts.builder()//
//                .setClaims(claims)//
//                .setIssuedAt(now)//
//                .setExpiration(expiration)//
//                .signWith(key, SignatureAlgorithm.HS256)//
//                .compact();
//    }

    public String createRefreshToken(Long id, List<AppUserRole> appUserRoles) {

        var refreshToken = createToken(id, appUserRoles, refreshTokenExpiration);
        tokenRepository.save(RefreshToken.builder().refreshToken(refreshToken).build());

        return refreshToken;
        //TODO RefreshToken DB 만료기간 삭제
    }

    public String createAccessToken(Long id, List<AppUserRole> appUserRoles){
        return createToken(id, appUserRoles, accessTokenExpiration);
    }

    private String createToken(Long id, List<AppUserRole> appUserRoles, Long expirationTime) {
        Claims claims = Jwts.claims().setSubject(id.toString());
        claims.put("auth", appUserRoles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(expiration)//
                .signWith(key, SignatureAlgorithm.HS256)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        var userEmail = userRepository.findEmailById(getId(token));
        UserDetails userDetails = myUserDetails.loadUserByUsername(userEmail.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Long getId(String token) {
        return Long.parseLong(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            System.out.println("validate Token : " + token);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            System.out.println("try success");

            if (redisUtil.hasKeyBlackList(token)) {
                System.out.println("redis error");
                throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
