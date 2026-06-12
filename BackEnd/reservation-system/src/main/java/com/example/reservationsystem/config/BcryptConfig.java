package com.example.reservationsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt 配置
 * 提供密码加密/验证的单向哈希工具
 */
@Configuration
public class BcryptConfig {

    /**
     * BCrypt 密码编码器
     * 强度 = 10（默认推荐值），每次加密自动生成随机盐
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
