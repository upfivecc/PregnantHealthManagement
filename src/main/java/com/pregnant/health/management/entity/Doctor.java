package com.pregnant.health.management.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Doctor {
    private Long id;
    private Long userId;
    private String hospital;
    private String department;
    private String title;
    private String specialty;
    private String introduction;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}