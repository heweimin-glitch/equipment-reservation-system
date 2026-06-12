package com.example.reservationsystem.controller;

import com.example.reservationsystem.entity.User;
import com.example.reservationsystem.service.UserService;
import com.example.reservationsystem.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接口
 * 提供登录（返回 JWT Token）、密码修改、管理员查询、用户注册等 REST API
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录
     * GET /user/login?id=xxx&password=xxx
     * @return { token: "JWT令牌", user: {用户信息} } 或 null
     */
    @GetMapping("/login")
    public Map<String, Object> login(
            @RequestParam String id,
            @RequestParam String password
    ) {
        User user = userService.login(id, password);
        if (user == null) {
            return null;
        }
        // 生成 JWT Token
        String token = jwtUtil.generateToken(user.getId(), user.getName(), user.getIs_admin());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    /**
     * 修改密码
     * POST /user/updatePassword
     * @param map {id, oldPassword, newPassword}
     * @return 成功返回 true
     */
    @PostMapping("/updatePassword")
    public boolean updatePassword(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        return userService.updatePassword(id, oldPassword, newPassword);
    }

    /**
     * 按学科前缀查询管理员列表（用户预约时选择审批人用）
     * GET /user/adminByPrefix?prefix=01
     */
    @GetMapping("/adminByPrefix")
    public List<User> getAdminsByPrefix(@RequestParam String prefix) {
        return userService.getAdminsByPrefix(prefix);
    }

    /**
     * 新增用户（管理员创建用户用）
     * POST /user/add
     */
    @PostMapping("/add")
    public boolean add(@RequestBody User user) {
        return userService.addUser(user);
    }

}