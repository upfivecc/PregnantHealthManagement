package com.pregnant.health.management.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Knowledge {
    private Long id;
    private String title;
    private String content;
    private String category;
    private Integer status;
    private Long createdBy;
    private String author; // 前端传递的作者字段
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}