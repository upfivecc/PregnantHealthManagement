package com.pregnant.health.management.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Consultation {
    private Long id;
    private Long userId;
    private Long doctorId;
    private String title;
    private String content;
    private String reply;
    private Integer status;
    private LocalDateTime createdTime;
    private LocalDateTime repliedTime;
    private LocalDateTime updatedTime;
}