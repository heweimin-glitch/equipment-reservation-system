package com.example.reservationsystem.service;

import com.example.reservationsystem.entity.Device;
import com.example.reservationsystem.entity.User;
import com.example.reservationsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String id,String password){
        User user = userMapper.findById(id);
        if(user == null){
            return null;
        }

        if(!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }

    public boolean updatePassword(String id,String oldPwd,String newPwd){
        User user = userMapper.findById(id);
        if(user == null){
            return false;
        }
        if(!user.getPassword().equals(oldPwd)){
            return false;
        }
        userMapper.updatePassword(id,newPwd);
        return true;
    }

    public List<User> getAdminsByPrefix(String prefix) {
        return userMapper.getAdminsByPrefix(prefix);
    }


    public boolean addUser(User user){
        if(userMapper.exists(user.getId()) > 0){
            return false;
        }
        userMapper.insert(user);
        return true;
    }



}