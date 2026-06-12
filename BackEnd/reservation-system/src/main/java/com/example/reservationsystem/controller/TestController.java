package com.example.reservationsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查接口
 * 用于验证后端服务是否正常启动
 */
@RestController
public class TestController {

    /** GET /test —— 返回启动成功消息 */
    @GetMapping("/test")
    public String test() {
        return "后端启动成功";
    }

}
