package com.example.reservationsystem.entity;
import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String password;
    private Integer is_admin;

}