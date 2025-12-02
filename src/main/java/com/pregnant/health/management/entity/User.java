package com.pregnant.health.management.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String realName;
    private Integer gender;
    private Integer age;
    private String role;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}