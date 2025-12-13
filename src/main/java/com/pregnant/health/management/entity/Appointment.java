package com.pregnant.health.management.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Appointment {
    private Long id;
    private Long userId;
    private Long doctorId;
    private LocalDateTime appointmentTime;
    private Integer status;
    private String remark;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    
    // 关联字段
    private String userName;
    private String userRealName;
    private String hospital;
    private String department;
    private String title;
    private String doctorName;
}