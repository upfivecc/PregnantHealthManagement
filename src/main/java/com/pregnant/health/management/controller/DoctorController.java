package com.pregnant.health.management.controller;

import com.pregnant.health.management.entity.Doctor;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
        PageResult<Doctor> result;
        if (realName != null && !realName.isEmpty()) {
            // 按姓名查询
            result = doctorService.getDoctorListByName(realName, page, size);
        } else {
            // 查询所有
            result = doctorService.getDoctorList(page, size);
        }
        return Result.success(result);
    }
    
    @PostMapping
    public Result<String> saveDoctor(@RequestParam("userId") Long userId,
                          @RequestParam("name") String name,
                          @RequestParam("hospital") String hospital,
                          @RequestParam("department") String department,
                          @RequestParam("title") String title,
                          @RequestParam("specialty") String specialty,
                          @RequestParam("introduction") String introduction,
                          @RequestParam("score") Double score,
                          @RequestParam("consultationCount") Integer consultationCount,
                          @RequestParam("positiveRate") Double positiveRate,
                          @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException {
        // 创建Doctor对象
        Doctor doctor = new Doctor();
        doctor.setUserId(userId);
        doctor.setName(name);
        doctor.setHospital(hospital);
        doctor.setDepartment(department);
        doctor.setTitle(title);
        doctor.setSpecialty(specialty);
        doctor.setIntroduction(introduction);
        doctor.setScore(score);
        doctor.setConsultationCount(consultationCount);
        doctor.setPositiveRate(positiveRate);
        
        // 处理头像上传
        if (avatar != null && !avatar.isEmpty()) {
            String avatarPath = saveAvatarFile(avatar);
            doctor.setAvatar(avatarPath);
        }
        
        boolean success = doctorService.saveDoctor(doctor);
        if (success) {
            return Result.success("添加医生成功");
        } else {
            return Result.error("添加医生失败");
        }
    }
    
    @PutMapping("/{id}")
    public Result<String> updateDoctor(@PathVariable Long id,
                          @RequestParam("userId") Long userId,
                          @RequestParam("name") String name,
                          @RequestParam("hospital") String hospital,
                          @RequestParam("department") String department,
                          @RequestParam("title") String title,
                          @RequestParam("specialty") String specialty,
                          @RequestParam("introduction") String introduction,
                          @RequestParam("score") Double score,
                          @RequestParam("consultationCount") Integer consultationCount,
                          @RequestParam("positiveRate") Double positiveRate,
                          @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException {
        // 创建Doctor对象
        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setUserId(userId);
        doctor.setName(name);
        doctor.setHospital(hospital);
        doctor.setDepartment(department);
        doctor.setTitle(title);
        doctor.setSpecialty(specialty);
        doctor.setIntroduction(introduction);
        doctor.setScore(score);
        doctor.setConsultationCount(consultationCount);
        doctor.setPositiveRate(positiveRate);
        
        // 处理头像上传
        if (avatar != null && !avatar.isEmpty()) {
            String avatarPath = saveAvatarFile(avatar);
            doctor.setAvatar(avatarPath);
        }
        
        boolean success = doctorService.updateDoctor(doctor);
        if (success) {
            return Result.success("更新医生成功");
        } else {
            return Result.error("更新医生失败");
        }
    }
    
    // 保存头像文件
    private String saveAvatarFile(MultipartFile avatar) throws IOException {
        // 确保上传目录存在
        String uploadDir = "uploads/avatars/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // 生成唯一的文件名
        String originalFilename = avatar.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
        
        // 保存文件
        Path filePath = uploadPath.resolve(uniqueFilename);
        avatar.transferTo(filePath);
        
        // 返回文件路径
        return "/" + uploadDir + uniqueFilename;
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