package com.example.backend.security;

import com.example.backend.config.JwtConfig;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Long idUser, String email, long role) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtConfig.getExpirationTime());

        return Jwts.builder()
                .setSubject(email) // Thiết lập subject là email người dùng
                .claim("id", idUser) // Thêm claim id người dùng
                .claim("role", role) // Thêm claim role vào JWT
                .setIssuedAt(now) // Thiết lập thời điểm phát hành
                .setExpiration(expirationDate) // Thiết lập thời gian hết hạn của token
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey()) // Ký với thuật toán HS256
                .compact(); // Trả về token đã được mã hóa
    }


    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String email) {
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
