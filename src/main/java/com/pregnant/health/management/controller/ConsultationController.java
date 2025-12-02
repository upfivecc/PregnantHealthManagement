package com.pregnant.health.management.controller;

import com.pregnant.health.management.entity.Consultation;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {
    
    @Autowired
    private ConsultationService consultationService;
    
    @GetMapping("/{id}")
    public Result<Consultation> getConsultationById(@PathVariable Long id) {
        Consultation consultation = consultationService.getById(id);
        if (consultation != null) {
            return Result.success(consultation);
        } else {
            return Result.error("咨询不存在");
        }
    }
    
    @GetMapping
    public Result<PageResult<Consultation>> getConsultationList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long doctorId) {
        PageResult<Consultation> result;
        if (doctorId != null) {
            result = consultationService.getConsultationListByDoctor(doctorId, page, size);
        } else {
            result = consultationService.getConsultationList(page, size);
        }
        return Result.success(result);
    }
    
    @GetMapping("/unreplied/count")
    public Result<Long> getUnrepliedCount() {
        Long count = consultationService.getUnrepliedCount();
        return Result.success(count);
    }
    
    @PostMapping
    public Result<String> saveConsultation(@RequestBody Consultation consultation) {
        boolean success = consultationService.saveConsultation(consultation);
        if (success) {
            return Result.success("咨询提交成功");
        } else {
            return Result.error("咨询提交失败");
        }
    }
    
    @PutMapping("/{id}/reply")
    public Result<String> replyConsultation(@PathVariable Long id, @RequestBody ReplyRequest request) {
        boolean success = consultationService.replyConsultation(id, request.getReply());
        if (success) {
            return Result.success("回复成功");
        } else {
            return Result.error("回复失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteConsultation(@PathVariable Long id) {
        boolean success = consultationService.deleteConsultation(id);
        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
    
    // 内部类用于回复请求
    public static class ReplyRequest {
        private String reply;
        
        public String getReply() {
            return reply;
        }
        
        public void setReply(String reply) {
            this.reply = reply;
        }
    }
}