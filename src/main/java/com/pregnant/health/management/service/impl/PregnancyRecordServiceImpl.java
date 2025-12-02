package com.pregnant.health.management.service.impl;

import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.entity.PregnancyRecord;
import com.pregnant.health.management.mapper.PregnancyRecordMapper;
import com.pregnant.health.management.service.PregnancyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PregnancyRecordServiceImpl implements PregnancyRecordService {
    
    @Autowired
    private PregnancyRecordMapper pregnancyRecordMapper;
    
    @Override
    public PregnancyRecord getById(Long id) {
        return pregnancyRecordMapper.selectById(id);
    }
    
    @Override
    public List<PregnancyRecord> getRecordsByUserId(Long userId) {
        return pregnancyRecordMapper.selectByUserId(userId);
    }
    
    @Override
    public PageResult<PregnancyRecord> getRecordsByUserIdWithPage(Long userId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<PregnancyRecord> records = pregnancyRecordMapper.selectByUserIdWithPage(userId, offset, size);
        Long total = pregnancyRecordMapper.countByUserId(userId);
        return new PageResult<>(records, total, page, size);
    }
    
    @Override
    public boolean saveRecord(PregnancyRecord record) {
        return pregnancyRecordMapper.insert(record) > 0;
    }
    
    @Override
    public boolean updateRecord(PregnancyRecord record) {
        return pregnancyRecordMapper.update(record) > 0;
    }
    
    @Override
    public boolean deleteRecord(Long id) {
        return pregnancyRecordMapper.deleteById(id) > 0;
    }
}