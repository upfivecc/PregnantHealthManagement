package com.pregnant.health.management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pregnant.health.management.controller.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求路径
        String uri = request.getRequestURI();
        
        // 放行登录相关接口和静态资源
        if (uri.startsWith("/api/users/login") || 
            uri.startsWith("/api/users/admin-login") || 
            uri.startsWith("/api/users/register") ||
            uri.startsWith("/api/users/set-session") ||
            uri.startsWith("/api/users/clear-session") ||
            uri.equals("/") || 
            uri.startsWith("/login") || 
            uri.startsWith("/admin-login") ||
            uri.startsWith("/css/") || 
            uri.startsWith("/js/") || 
            uri.startsWith("/images/")) {
            return true;
        }
        
        // 检查session中是否有用户信息
        HttpSession session = request.getSession();
        Object currentUser = session.getAttribute("currentUser");
        
        if (currentUser == null) {
            // 未登录，返回未登录信息
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            Result<Object> result = Result.error("请先登录");
            ObjectMapper mapper = new ObjectMapper();
            out.write(mapper.writeValueAsString(result));
            out.flush();
            out.close();
            return false;
        }
        
        return true;
    }
}