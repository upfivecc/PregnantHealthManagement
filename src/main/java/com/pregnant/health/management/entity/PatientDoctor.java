package com.pregnant.health.management.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PatientDoctor {
    private Long id;
    private Long userId;
    private Long doctorId;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}