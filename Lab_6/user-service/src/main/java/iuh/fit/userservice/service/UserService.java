package iuh.fit.userservice.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import iuh.fit.userservice.entity.User;
import iuh.fit.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public boolean register(User user ){
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username đã tồn tại!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public Map<String, Object> login(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null) {
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu!"); // Đã thêm chữ "throw"
        }

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Sai tài khoản hoặc mật khẩu!");
        }

        // Generate token
        String accessToken = jwtService.generateAccessToken(existingUser.getUsername());
        String refreshToken = jwtService.generateRefreshToken(existingUser.getUsername());

        // Gom dữ liệu trả về
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);

        // Lọc thông tin user (Tuyệt đối không trả về password)
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", existingUser.getId());
        userInfo.put("username", existingUser.getUsername());

        result.put("user", userInfo);

        return result;
    }
    // Logic Refresh Token
    public Map<String, String> refreshToken(String refreshToken) {
        if (refreshToken == null || !jwtService.isTokenValid(refreshToken)) {
            throw new RuntimeException("Refresh Token không hợp lệ hoặc đã hết hạn!");
        }

        String username = jwtService.extractUsername(refreshToken);

        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("User không còn tồn tại!");
        }

        String newAccessToken = jwtService.generateAccessToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }
}
