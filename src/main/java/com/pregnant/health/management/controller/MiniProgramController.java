package com.pregnant.health.management.controller;

import com.pregnant.health.management.entity.*;
import com.pregnant.health.management.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/mini-program")
public class MiniProgramController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private ConsultationService consultationService;
    
    @Autowired
    private PregnancyRecordService pregnancyRecordService;
    
    @Autowired
    private KnowledgeService knowledgeService;
    
    /**
     * 小程序用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        try {
            // 设置默认角色为普通用户
            user.setRole("USER");
            User registeredUser = userService.register(user);
            return Result.success(registeredUser);
        } catch (Exception e) {
            return Result.error("注册失败：" + e.getMessage());
        }
    }
    
    /**
     * 小程序用户登录
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody User loginUser) {
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user != null && "USER".equals(user.getRole())) {
            return Result.success(user);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
    
    /**
     * 获取医生列表
     */
    @GetMapping("/doctors")
    public Result<PageResult<Doctor>> getDoctors(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageResult<Doctor> result = doctorService.getDoctorList(page, size);
        return Result.success(result);
    }
    
    /**
     * 私信咨询医生
     */
    @PostMapping("/consultations")
    public Result<String> consultDoctor(@RequestBody Consultation consultation) {
        // 设置默认状态为未回复
        consultation.setStatus(0);
        boolean success = consultationService.saveConsultation(consultation);
        if (success) {
            return Result.success("咨询提交成功");
        } else {
            return Result.error("咨询提交失败");
        }
    }
    
    /**
     * 获取我的咨询列表
     */
    @GetMapping("/consultations/my")
    public Result<PageResult<Consultation>> getMyConsultations(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        // 这里简化处理，实际应该根据当前登录用户获取
        PageResult<Consultation> result = consultationService.getConsultationList(page, size);
        return Result.success(result);
    }
    
    /**
     * 保存孕期档案
     */
    @PostMapping("/pregnancy-records")
    public Result<String> savePregnancyRecord(@RequestBody PregnancyRecord record) {
        boolean success = pregnancyRecordService.saveRecord(record);
        if (success) {
            return Result.success("保存成功");
        } else {
            return Result.error("保存失败");
        }
    }
    
    /**
     * 更新孕期档案
     */
    @PutMapping("/pregnancy-records")
    public Result<String> updatePregnancyRecord(@RequestBody PregnancyRecord record) {
        boolean success = pregnancyRecordService.updateRecord(record);
        if (success) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }
    
    /**
     * 获取我的孕期档案列表
     */
    @GetMapping("/pregnancy-records/my")
    public Result<List<PregnancyRecord>> getMyPregnancyRecords(@RequestParam Long userId) {
        // 这里简化处理，实际应该根据当前登录用户获取
        List<PregnancyRecord> records = pregnancyRecordService.getRecordsByUserId(userId);
        return Result.success(records);
    }
    
    /**
     * 获取孕期知识列表
     */
    @GetMapping("/knowledge")
    public Result<PageResult<Knowledge>> getKnowledge(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageResult<Knowledge> result = knowledgeService.getPublishedKnowledgeList(page, size);
        return Result.success(result);
    }
    
    /**
     * 获取孕期知识详情
     */
    @GetMapping("/knowledge/{id}")
    public Result<Knowledge> getKnowledgeDetail(@PathVariable Long id) {
        Knowledge knowledge = knowledgeService.getById(id);
        if (knowledge != null && knowledge.getStatus() == 1) {
            return Result.success(knowledge);
        } else {
            return Result.error("知识不存在或未发布");
        }
    }
}