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
}