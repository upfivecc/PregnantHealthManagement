package com.pregnant.health.management.entity;

import lombok.Data;
import java.util.List;

@Data
public class PageResult<T> {
    private Long total;
    private List<T> records;
    private Integer currentPage;
    private Integer pageSize;
    
    public PageResult() {}
    
    public PageResult(List<T> records, Long total, Integer currentPage, Integer pageSize) {
        this.records = records;
        this.total = total;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}