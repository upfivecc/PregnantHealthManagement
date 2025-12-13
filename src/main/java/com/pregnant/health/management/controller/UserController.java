package com.pregnant.health.management.controller;

import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.entity.User;
import com.pregnant.health.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            return Result.success(registeredUser);
        } catch (Exception e) {
            return Result.error("注册失败：" + e.getMessage());
        }
    }
    
    @PostMapping
    public Result<User> addUser(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            return Result.success(registeredUser);
        } catch (Exception e) {
            return Result.error("添加用户失败：" + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public Result<User> login(@RequestBody User loginUser, HttpServletRequest request) {
        User user = userService.loginByUsernameOrPhone(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            // 将用户信息保存到session中
            request.getSession().setAttribute("currentUser", user);
            return Result.success(user);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
    
    @PostMapping("/admin-login")
    public Result<User> adminLogin(@RequestBody User loginUser, HttpServletRequest request) {
        User user = userService.loginByUsernameOrPhone(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            // 验证角色是否匹配
            if (loginUser.getRole() != null && !loginUser.getRole().equals(user.getRole())) {
                return Result.error("用户角色不匹配");
            }
            
            // 只允许管理员和医生登录管理后台
            if (!"ADMIN".equals(user.getRole()) && !"DOCTOR".equals(user.getRole())) {
                return Result.error("您没有权限登录管理后台");
            }
            
            // 将用户信息保存到session中
            request.getSession().setAttribute("currentUser", user);
            
            return Result.success(user);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
    
    @PostMapping("/set-session")
    public Result<String> setSession(@RequestBody User user, HttpServletRequest request) {
        // 将用户信息保存到session中
        request.getSession().setAttribute("currentUser", user);
        return Result.success("Session设置成功");
    }
    
    @PostMapping("/clear-session")
    public Result<String> clearSession(HttpServletRequest request) {
        // 清除session中的用户信息
        request.getSession().removeAttribute("currentUser");
        return Result.success("Session清除成功");
    }
    
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("用户不存在");
        }
    }
    
    @GetMapping
    public Result<PageResult<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String role) {
        PageResult<User> result = userService.getUserList(page, size, role);
        return Result.success(result);
    }
    
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        // 设置用户ID
        user.setId(id);
        boolean success = userService.updateUser(user);
        if (success) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }
    
    @PutMapping("/{id}/password")
    public Result<String> updatePassword(@PathVariable Long id, @RequestBody PasswordRequest request) {
        boolean success = userService.updatePassword(id, request.getNewPassword());
        if (success) {
            return Result.success("密码修改成功");
        } else {
            return Result.error("密码修改失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
    
    // 内部类用于密码修改请求
    public static class PasswordRequest {
        private String newPassword;
        
        public String getNewPassword() {
            return newPassword;
        }
        
        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}