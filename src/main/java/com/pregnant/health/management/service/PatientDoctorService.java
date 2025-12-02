package com.pregnant.health.management.service;

import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.entity.PatientDoctor;
import com.pregnant.health.management.entity.User;

public interface PatientDoctorService {
    
    PatientDoctor getById(Long id);
    
    PatientDoctor getByUserAndDoctor(Long userId, Long doctorId);
    
    PageResult<User> getPatientsByDoctor(Long doctorId, Integer page, Integer size);
    
    boolean savePatientDoctor(PatientDoctor patientDoctor);
    
    boolean updatePatientDoctorStatus(Long id, Integer status);
    
    boolean updatePatientDoctorStatusByUserAndDoctor(Long userId, Long doctorId, Integer status);
}