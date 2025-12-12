package com.pregnant.health.management.service;

import com.pregnant.health.management.entity.Doctor;
import com.pregnant.health.management.entity.PageResult;

public interface DoctorService {
    
    Doctor getById(Long id);
    
    Doctor getByUserId(Long userId);
    
    PageResult<Doctor> getDoctorList(Integer page, Integer size);
    
    // 按姓名查询医生列表
    PageResult<Doctor> getDoctorListByName(String realName, Integer page, Integer size);
    
    // 复合条件查询医生列表
    PageResult<Doctor> getDoctorList(Integer page, Integer size, String realName);
    
    boolean saveDoctor(Doctor doctor);
    
    boolean updateDoctor(Doctor doctor);
    
    boolean deleteDoctor(Long id);
}