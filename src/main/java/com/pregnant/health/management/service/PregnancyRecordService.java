package com.pregnant.health.management.service;

import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.entity.PregnancyRecord;

import java.util.List;

public interface PregnancyRecordService {
    
    PregnancyRecord getById(Long id);
    
    List<PregnancyRecord> getRecordsByUserId(Long userId);
    
    PageResult<PregnancyRecord> getRecordsByUserIdWithPage(Long userId, Integer page, Integer size);
    
    boolean saveRecord(PregnancyRecord record);
    
    boolean updateRecord(PregnancyRecord record);
    
    boolean deleteRecord(Long id);
}