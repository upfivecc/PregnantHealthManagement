package com.pregnant.health.management.controller;

import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.entity.PatientDoctor;
import com.pregnant.health.management.entity.User;
import com.pregnant.health.management.service.PatientDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient-doctor")
public class PatientDoctorController {
    
    @Autowired
    private PatientDoctorService patientDoctorService;
    
    @GetMapping("/{id}")
    public Result<PatientDoctor> getPatientDoctorById(@PathVariable Long id) {
        PatientDoctor patientDoctor = patientDoctorService.getById(id);
        if (patientDoctor != null) {
            return Result.success(patientDoctor);
        } else {
            return Result.error("关系不存在");
        }
    }
    
    @GetMapping("/relation")
    public Result<PatientDoctor> getPatientDoctorByUserAndDoctor(
            @RequestParam Long userId, 
            @RequestParam Long doctorId) {
        PatientDoctor patientDoctor = patientDoctorService.getByUserAndDoctor(userId, doctorId);
        if (patientDoctor != null) {
            return Result.success(patientDoctor);
        } else {
            return Result.error("关系不存在");
        }
    }
    
    @GetMapping("/patients/{doctorId}")
    public Result<PageResult<User>> getPatientsByDoctor(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageResult<User> result = patientDoctorService.getPatientsByDoctor(doctorId, page, size);
        return Result.success(result);
    }
    
    @PostMapping
    public Result<String> savePatientDoctor(@RequestBody PatientDoctor patientDoctor) {
        boolean success = patientDoctorService.savePatientDoctor(patientDoctor);
        if (success) {
            return Result.success("关系建立成功");
        } else {
            return Result.error("关系建立失败");
        }
    }
    
    @PutMapping("/{id}/status")
    public Result<String> updatePatientDoctorStatus(@PathVariable Long id, @RequestParam Integer status) {
        boolean success = patientDoctorService.updatePatientDoctorStatus(id, status);
        if (success) {
            return Result.success("状态更新成功");
        } else {
            return Result.error("状态更新失败");
        }
    }
    
    @PutMapping("/relation/status")
    public Result<String> updatePatientDoctorStatusByUserAndDoctor(
            @RequestParam Long userId, 
            @RequestParam Long doctorId, 
            @RequestParam Integer status) {
        boolean success = patientDoctorService.updatePatientDoctorStatusByUserAndDoctor(userId, doctorId, status);
        if (success) {
            return Result.success("状态更新成功");
        } else {
            return Result.error("状态更新失败");
        }
    }
}