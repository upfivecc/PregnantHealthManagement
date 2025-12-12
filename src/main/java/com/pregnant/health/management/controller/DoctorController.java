package com.pregnant.health.management.controller;

import com.pregnant.health.management.entity.Doctor;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    
    @Autowired
 private DoctorService doctorService;
    
    @GetMapping("/{id}")
    public Result<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getById(id);
        if (doctor != null) {
            return Result.success(doctor);
        } else {
            return Result.error("医生不存在");
        }
    }
    
    @GetMapping("/user/{userId}")
    public Result<Doctor> getDoctorByUserId(@PathVariable Long userId) {
        Doctor doctor = doctorService.getByUserId(userId);
        if (doctor != null) {
            return Result.success(doctor);
        } else {
            return Result.error("医生不存在");
        }
    }
    
    @GetMapping
    public Result<PageResult<Doctor>> getDoctorList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String realName) {
        PageResult<Doctor> result = doctorService.getDoctorList(page, size, realName);
        return Result.success(result);
    }
    
    @PostMapping
    public Result<String> saveDoctor(@RequestBody Doctor doctor) {
        boolean success = doctorService.saveDoctor(doctor);
        if (success) {
            return Result.success("添加医生成功");
        } else {
            return Result.error("添加医生失败");
        }
    }
    
    @PutMapping("/{id}")
    public Result<String> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        doctor.setId(id);
        boolean success = doctorService.updateDoctor(doctor);
        if (success) {
            return Result.success("更新医生成功");
        } else {
            return Result.error("更新医生失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteDoctor(@PathVariable Long id) {
        boolean success = doctorService.deleteDoctor(id);
        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
}