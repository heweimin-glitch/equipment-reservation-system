package com.example.reservationsystem.interceptor;

import com.example.reservationsystem.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 认证拦截器
 * 对每个请求验证 Authorization Header 中的 Token
 * 白名单路径直接放行，其他路径校验 Token 有效性
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /** 白名单：无需认证的路径 */
    private static final String[] WHITELIST = {
            "/test",
            "/user/login"
    };

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String path = request.getRequestURI();

        // 白名单路径直接放行
        for (String white : WHITELIST) {
            if (path.startsWith(white)) {
                return true;
            }
        }

        // 从 Header 中提取 Token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            return false;
        }

        String token = authHeader.substring(7);

        // 验证 Token 有效性
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(401);
            return false;
        }

        // 将用户信息写入请求属性，供 Controller 使用
        Claims claims = jwtUtil.parseToken(token);
        request.setAttribute("userId", claims.get("userId"));
        request.setAttribute("userName", claims.get("userName"));
        request.setAttribute("isAdmin", claims.get("isAdmin"));

        return true;
    }
}
