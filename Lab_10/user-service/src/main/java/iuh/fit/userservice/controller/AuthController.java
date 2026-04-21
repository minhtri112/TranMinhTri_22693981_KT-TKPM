package iuh.fit.userservice.controller;


import iuh.fit.userservice.entity.User;
import iuh.fit.userservice.repository.UserRepository;
import iuh.fit.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private UserService userService;
    // 1. API: ĐĂNG KÝ
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok("Đăng ký thành công!");
        } catch (IllegalArgumentException e) {
            // Lỗi do Username đã tồn tại
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. API: ĐĂNG NHẬP
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Lấy kết quả từ Service
            Map<String, Object> loginResult = userService.login(user);

            // Bóc tách dữ liệu
            String refreshToken = (String) loginResult.get("refreshToken");
            String accessToken = (String) loginResult.get("accessToken");
            Object userInfo = loginResult.get("user");

            // Tạo HttpOnly Cookie chứa Refresh Token
            ResponseCookie springCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)    // JavaScript (Frontend) không thể đọc được cookie này
                    .secure(false)     // Để false khi chạy localhost HTTP. Khi lên môi trường thật có HTTPS thì đổi thành true
                    .path("/")         // Cookie có tác dụng trên toàn bộ đường dẫn của app
                    .maxAge(7 * 24 * 60 * 60) // Thời gian sống: 7 ngày (trùng với hạn của token)
                    .sameSite("Strict") // Chống tấn công CSRF
                    .build();

            // Body trả về cho Frontend (Chỉ chứa accessToken và user info)
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("accessToken", accessToken);
            responseBody.put("user", userInfo);

            // Trả về Response: Header chứa Cookie, Body chứa JSON
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(responseBody);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // 3. API: REFRESH TOKEN
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            Map<String, String> tokens = userService.refreshToken(refreshToken);
            return ResponseEntity.ok(tokens);
        } catch (RuntimeException e) {
            // Lỗi do token hết hạn, không hợp lệ hoặc user không tồn tại
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
