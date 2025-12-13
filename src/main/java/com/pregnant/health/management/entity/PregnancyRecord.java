package com.pregnant.health.management.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Data
public class PregnancyRecord {
    private Long id;
    private Long userId;
    private BigDecimal height;
    private BigDecimal weight;
    private Integer bloodPressureHigh;
    private Integer bloodPressureLow;
    private Integer fetalMovementCount;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate recordDate;
    
    private String remark;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}