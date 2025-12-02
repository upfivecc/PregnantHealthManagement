package com.pregnant.health.management.controller;

import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.entity.PregnancyRecord;
import com.pregnant.health.management.service.PregnancyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pregnancy-records")
public class PregnancyRecordController {
    
    @Autowired
    private PregnancyRecordService pregnancyRecordService;
    
    @GetMapping("/{id}")
    public Result<PregnancyRecord> getRecordById(@PathVariable Long id) {
        PregnancyRecord record = pregnancyRecordService.getById(id);
        if (record != null) {
            return Result.success(record);
        } else {
            return Result.error("记录不存在");
        }
    }
    
    @GetMapping("/user/{userId}")
    public Result<List<PregnancyRecord>> getRecordsByUserId(@PathVariable Long userId) {
        List<PregnancyRecord> records = pregnancyRecordService.getRecordsByUserId(userId);
        return Result.success(records);
    }
    
    @GetMapping("/user/{userId}/page")
    public Result<PageResult<PregnancyRecord>> getRecordsByUserIdWithPage(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageResult<PregnancyRecord> result = pregnancyRecordService.getRecordsByUserIdWithPage(userId, page, size);
        return Result.success(result);
    }
    
    @PostMapping
    public Result<String> saveRecord(@RequestBody PregnancyRecord record) {
        boolean success = pregnancyRecordService.saveRecord(record);
        if (success) {
            return Result.success("保存成功");
        } else {
            return Result.error("保存失败");
        }
    }
    
    @PutMapping
    public Result<String> updateRecord(@RequestBody PregnancyRecord record) {
        boolean success = pregnancyRecordService.updateRecord(record);
        if (success) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteRecord(@PathVariable Long id) {
        boolean success = pregnancyRecordService.deleteRecord(id);
        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
}