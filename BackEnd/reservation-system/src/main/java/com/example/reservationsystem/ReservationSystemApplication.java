package com.example.reservationsystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 实验设备预约系统——Spring Boot 启动类
 * 自动配置 MyBatis 数据源，扫描 Mapper 接口
 */
@SpringBootApplication
public class ReservationSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservationSystemApplication.class, args);
    }
}