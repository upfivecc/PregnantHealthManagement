package com.pregnant.health.management.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Doctor {
    private Long id;
    private Long userId;
    private String name;
    private String hospital;
    private String department;
    private String title;
    private String specialty;
    private String introduction;
    private Double score;
    private Integer consultationCount;
    private Double positiveRate;
    private String avatar;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}