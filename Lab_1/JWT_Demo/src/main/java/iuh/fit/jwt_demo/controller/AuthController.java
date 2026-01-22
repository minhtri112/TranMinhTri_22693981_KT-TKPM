package iuh.fit.jwt_demo.controller;


import io.jsonwebtoken.Claims;
import iuh.fit.jwt_demo.dto.LoginRequest;
import iuh.fit.jwt_demo.security.jwt.JwtUtil;
import iuh.fit.jwt_demo.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpServletResponse response){
        System.out.println(req.getPassword());
        System.out.println(req.getUsername());
        if (!req.getUsername().equals("admin") || !req.getPassword().equals("123")) {
            return ResponseEntity.status(401).build();
        }
        Long userID = 1L;
        String accessToken = jwtUtil.generateAccessToken(userID);
        String refreshToken = jwtUtil.generateRefreshToken(userID);
        ResponseCookie cookie = ResponseCookie.from("refreshToken",refreshToken)
                .httpOnly(true)
                .path("/auth")
                .maxAge(Duration.ofHours(1))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
        return ResponseEntity.ok(Map.of("accessToken", accessToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(name = "refreshToken", required = true) String token){
        if(token == null) return ResponseEntity.status(401).build();
        Claims claims = jwtUtil.parseRefreshToken(token);
        if(tokenBlacklistService.isBackList(claims.getId())){
            return ResponseEntity.status(401).build();
        }

        Long userID = Long.parseLong(claims.getSubject());
        System.out.println(userID);
        String newAccessToken = jwtUtil.generateAccessToken(userID);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @CookieValue(name = "refreshToken", required = true) String refreshToken,
            HttpServletResponse response) {


        System.out.println(refreshToken);

        if (refreshToken != null) {
            Claims claims = jwtUtil.parseRefreshToken(refreshToken);

            long ttl = (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000;

            if (ttl > 0) {
                tokenBlacklistService.backList(claims.getId(), ttl);
            }
        }


        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/auth")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }


}
