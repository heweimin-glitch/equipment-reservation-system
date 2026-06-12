package com.example.reservationsystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * 负责 Token 的生成、解析和验证
 */
@Component
public class JwtUtil {

    /** 密钥（生产环境应从配置中心获取更复杂的值） */
    @Value("${jwt.secret:EquipmentReservationSystemSecretKey2026!@#$%^&*()}")
    private String secret;

    /** Token 有效期（毫秒），默认 7 天 */
    @Value("${jwt.expiration:604800000}")
    private long expiration;

    /** 获取签名密钥 */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 JWT Token
     * @param userId   用户ID
     * @param userName 用户姓名
     * @param isAdmin  是否管理员
     * @return JWT Token 字符串
     */
    public String generateToken(String userId, String userName, Integer isAdmin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userName", userName);
        claims.put("isAdmin", isAdmin);

        Date now = new Date();
        return Jwts.builder()
                .claims(claims)
                .subject(userId)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 从 Token 中解析所有声明信息
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 验证 Token 是否有效
     * @return true=有效，false=无效/过期
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 Token 中提取用户ID
     */
    public String getUserId(String token) {
        return parseToken(token).getSubject();
    }
}
