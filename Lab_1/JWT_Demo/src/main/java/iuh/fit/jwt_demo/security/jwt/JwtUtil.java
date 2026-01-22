package iuh.fit.jwt_demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final String ACCESS_TOKEN = "access-secret-key-which-is-at-least-32-characters-long!!";
    private final String REFRESH_TOKEN = "REFRESH_TOKEN-secret-key-which-is-at-least-32-characters-long!!";
    // tg het han
    private long ACCESS_TOKEN_EXP = 15*60*1000; // 15p;
    private long REFRESH_TOKEN_EXP = 1*60*60*1000; // 1h;

    public String generateAccessToken(Long userID){
        return Jwts.builder()
                .setSubject(userID.toString())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP))
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userID){
        return Jwts.builder()
                .setSubject(userID.toString())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP))
                .signWith(Keys.hmacShaKeyFor(REFRESH_TOKEN.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseAccessToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims parseRefreshToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(REFRESH_TOKEN.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
