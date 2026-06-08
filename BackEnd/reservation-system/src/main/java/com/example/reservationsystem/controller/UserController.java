package com.example.reservationsystem.controller;
import com.example.reservationsystem.entity.User;
import com.example.reservationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public User login(
            @RequestParam String id,
            @RequestParam String password
    ){
        return userService.login(id,password);

    }

    @PostMapping("/updatePassword")
    public boolean updatePassword(@RequestBody Map<String,String> map){
        String id = map.get("id");
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        return userService.updatePassword(id,oldPassword,newPassword);
    }
    @GetMapping("/adminByPrefix")
    public List<User> getAdminsByPrefix(@RequestParam String prefix) {
        return userService.getAdminsByPrefix(prefix);
    }

    @PostMapping("/add")
    public boolean add(@RequestBody User user){
        return userService.addUser(user);
    }



}