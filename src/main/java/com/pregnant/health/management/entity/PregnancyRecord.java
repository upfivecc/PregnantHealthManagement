package com.pregnant.health.management.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PregnancyRecord {
    private Long id;
    private Long userId;
    private BigDecimal height;
    private BigDecimal weight;
    private Integer bloodPressureHigh;
    private Integer bloodPressureLow;
    private Integer fetalMovementCount;
    private LocalDate recordDate;
    private String remark;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}