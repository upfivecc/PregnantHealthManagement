package com.pregnant.health.management.service;

import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.entity.User;

public interface UserService {
    
    User register(User user);
    
    User login(String username, String password);
    
    User getById(Long id);
    
    PageResult<User> getUserList(Integer page, Integer size, String role);
    
    boolean updateUser(User user);
    
    boolean updatePassword(Long id, String newPassword);
    
    boolean deleteUser(Long id);
    
    Long getUserCountByRole(String role);
}