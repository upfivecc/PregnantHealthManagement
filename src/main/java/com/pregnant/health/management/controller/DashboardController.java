package com.pregnant.health.management.controller;

import com.pregnant.health.management.service.AppointmentService;
import com.pregnant.health.management.service.ConsultationService;
import com.pregnant.health.management.service.DoctorService;
import com.pregnant.health.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private ConsultationService consultationService;
    
    @Autowired
    private AppointmentService appointmentService;
    
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 用户总数
        Long userCount = userService.getUserCountByRole("USER");
        statistics.put("userCount", userCount);
        
        // 医生总数
        Long doctorCount = userService.getUserCountByRole("DOCTOR");
        statistics.put("doctorCount", doctorCount);
        
        // 未回复咨询数
        Long unrepliedCount = consultationService.getUnrepliedCount();
        statistics.put("unrepliedCount", unrepliedCount);
        
        // 今日预约数（这里简化处理，实际应查询今天的预约数）
        statistics.put("todayAppointmentCount", 0);
        
        return Result.success(statistics);
    }
    
    @GetMapping("/appointment-trend")
    public Result<List<Map<String, Object>>> getAppointmentTrend() {
        try {
            // 从数据库获取真实的预约趋势数据
            List<Map<String, Object>> trendData = appointmentService.getAppointmentTrend();
            return Result.success(trendData);
        } catch (Exception e) {
            // 如果出现异常，返回空数据
            return Result.success(new ArrayList<>());
        }
    }
    
    @GetMapping("/patient-stats")
    public Result<List<Map<String, Object>>> getPatientStats() {
        try {
            // 直接从用户表获取普通用户统计数据，而不是从预约表
            Long totalPatients = userService.getUserCountByRole("USER");
            
            // 构造返回格式，与前端期望一致
            List<Map<String, Object>> statsData = new ArrayList<>();
            Map<String, Object> data = new HashMap<>();
            data.put("date", "总计");
            data.put("count", totalPatients);
            statsData.add(data);
            
            return Result.success(statsData);
        } catch (Exception e) {
            // 如果出现异常，返回空数据
            return Result.success(new ArrayList<>());
        }
    }
}