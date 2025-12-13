package com.pregnant.health.management.service.impl;

import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.entity.User;
import com.pregnant.health.management.mapper.UserMapper;
import com.pregnant.health.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User register(User user) {
        // 使用明文密码存储
        userMapper.insert(user);
        return user;
    }
    
    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        // 使用明文密码验证
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    @Override
    public User loginByUsernameOrPhone(String username, String password) {
        User user = userMapper.selectByUsernameOrPhone(username);
        // 使用明文密码验证
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }
    
    @Override
    public PageResult<User> getUserList(Integer page, Integer size, String role) {
        int offset = (page - 1) * size;
        List<User> users;
        Long total;
        
        if (role != null && !role.isEmpty()) {
            users = userMapper.selectByRole(role, offset, size);
            total = userMapper.countByRole(role);
        } else {
            users = userMapper.selectAll(offset, size);
            total = userMapper.countAll();
        }
        
        return new PageResult<>(users, total, page, size);
    }
    
    @Override
    public boolean updateUser(User user) {
        return userMapper.update(user) > 0;
    }
    
    @Override
    public boolean updatePassword(Long id, String newPassword) {
        // 使用明文密码更新
        return userMapper.updatePassword(id, newPassword) > 0;
    }
    
    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }
    
    @Override
    public Long getUserCountByRole(String role) {
        return userMapper.countByRole(role);
    }
    
    // 使用明文密码，不进行加密处理
}