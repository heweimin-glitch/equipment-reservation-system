package com.example.reservationsystem.service;

import com.example.reservationsystem.entity.User;
import com.example.reservationsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务逻辑层
 * 处理登录认证、密码修改、用户管理等。
 * 密码使用 BCrypt 加密存储。
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 用户登录验证（BCrypt 密码比对）
     * 兼容历史明文密码：首次登录时自动升级为 BCrypt 密文
     * @param id 用户ID
     * @param password 明文密码
     * @return 验证成功返回用户对象，失败返回 null
     */
    public User login(String id, String password) {
        User user = userMapper.findById(id);
        if (user == null) {
            return null;
        }

        String storedPwd = user.getPassword();

        // BCrypt 密文特征：以 $2a$ 开头、长度为 60
        if (storedPwd.startsWith("$2a$") && storedPwd.length() == 60) {
            // 已是 BCrypt 密文 —— 直接比对
            if (!passwordEncoder.matches(password, storedPwd)) {
                return null;
            }
        } else {
            // 历史明文密码 —— 明文比对
            if (!storedPwd.equals(password)) {
                return null;
            }
            // 自动升级为 BCrypt 密文
            userMapper.updatePassword(id, passwordEncoder.encode(password));
        }
        return user;
    }

    /**
     * 修改密码
     * @param id 用户ID
     * @param oldPwd 旧密码（明文，用于验证身份）
     * @param newPwd 新密码（明文，将 BCrypt 加密后存储）
     * @return 修改成功返回 true
     */
    public boolean updatePassword(String id, String oldPwd, String newPwd) {
        User user = userMapper.findById(id);
        if (user == null) {
            return false;
        }

        String storedPwd = user.getPassword();
        boolean oldPwdOk;

        if (storedPwd.startsWith("$2a$") && storedPwd.length() == 60) {
            // BCrypt 密文验证
            oldPwdOk = passwordEncoder.matches(oldPwd, storedPwd);
        } else {
            // 历史明文验证
            oldPwdOk = storedPwd.equals(oldPwd);
        }
        if (!oldPwdOk) {
            return false;
        }

        // 新密码 BCrypt 加密存储
        userMapper.updatePassword(id, passwordEncoder.encode(newPwd));
        return true;
    }

    /**
     * 按学科前缀查询管理员列表
     * @param prefix ID前2位（学科编号）
     * @return 该学科下的管理员用户列表
     */
    public List<User> getAdminsByPrefix(String prefix) {
        return userMapper.getAdminsByPrefix(prefix);
    }

    /**
     * 新增用户（注册）
     * 密码使用 BCrypt 加密后存储
     * @param user 用户对象（密码为明文，由本方法加密）
     * @return 成功返回 true，ID重复返回 false
     */
    public boolean addUser(User user) {
        if (userMapper.exists(user.getId()) > 0) {
            return false;
        }
        // 明文密码 → BCrypt 密文
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        return true;
    }

}