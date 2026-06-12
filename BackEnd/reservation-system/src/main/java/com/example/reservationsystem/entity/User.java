package com.example.reservationsystem.entity;
import lombok.Data;

/**
 * 用户实体
 * 对应数据库 Users 表，存储用户基本信息与权限
 */
@Data
public class User {
    /** 用户ID（11位编号） */
    private String id;
    /** 用户姓名 */
    private String name;
    /** 登录密码 */
    private String password;
    /** 是否管理员：0=普通用户，1=管理员 */
    private Integer is_admin;

}