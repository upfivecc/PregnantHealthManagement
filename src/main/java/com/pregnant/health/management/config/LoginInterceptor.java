package com.pregnant.health.management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pregnant.health.management.controller.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查session中是否有用户信息
        HttpSession session = request.getSession();
        Object currentUser = session.getAttribute("currentUser");
        
        if (currentUser == null) {
            // 未登录，返回JSON格式的错误响应
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            
            // 创建错误响应对象
            Result result = new Result();
            result.setCode(401);
            result.setMessage("未登录或登录已过期，请重新登录");
            
            // 转换为JSON字符串
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            
            writer.write(json);
            writer.flush();
            writer.close();
            
            return false;
        }
        
        return true;
    }
}