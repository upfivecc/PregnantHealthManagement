package com.pregnant.health.management.service;

import com.pregnant.health.management.entity.Doctor;
import com.pregnant.health.management.entity.PageResult;

public interface DoctorService {
    
    Doctor getById(Long id);
    
    Doctor getByUserId(Long userId);
    
    PageResult<Doctor> getDoctorList(Integer page, Integer size);
    
    boolean saveDoctor(Doctor doctor);
    
    boolean updateDoctor(Doctor doctor);
    
    boolean deleteDoctor(Long id);
}