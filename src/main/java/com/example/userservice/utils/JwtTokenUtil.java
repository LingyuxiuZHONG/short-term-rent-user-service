package com.example.userservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "yourSecretKey";  // 替换为你的密钥
    private static final long EXPIRATION_TIME = 86400000L;  // 1天过期时间（以毫秒为单位）

    // 生成 JWT token
    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);
        return JWT.create()
                .withSubject(username)  // 设置主题为用户名
                .withIssuedAt(new Date())  // 设置签发时间
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // 设置过期时间
                .sign(algorithm);  // 使用算法签名
    }

    // 从 token 中获取用户名
    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token);
        return decodedJWT.getSubject();  // 获取主题，即用户名
    }

    // 校验 token 是否有效
    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                    .build()
                    .verify(token);  // 验证 token
            return !decodedJWT.getExpiresAt().before(new Date());  // 检查是否过期
        } catch (Exception e) {
            return false;  // 如果验证失败或 token 无效，返回 false
        }
    }
}
