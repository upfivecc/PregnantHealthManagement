package com.pregnant.health.management.controller;

import com.pregnant.health.management.service.ConsultationService;
import com.pregnant.health.management.service.DoctorService;
import com.pregnant.health.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private ConsultationService consultationService;
    
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
}