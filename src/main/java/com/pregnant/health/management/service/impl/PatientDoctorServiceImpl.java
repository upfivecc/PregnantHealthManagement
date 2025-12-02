package com.pregnant.health.management.service.impl;

import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.entity.PatientDoctor;
import com.pregnant.health.management.entity.User;
import com.pregnant.health.management.mapper.PatientDoctorMapper;
import com.pregnant.health.management.service.PatientDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientDoctorServiceImpl implements PatientDoctorService {
    
    @Autowired
    private PatientDoctorMapper patientDoctorMapper;
    
    @Override
    public PatientDoctor getById(Long id) {
        return patientDoctorMapper.selectById(id);
    }
    
    @Override
    public PatientDoctor getByUserAndDoctor(Long userId, Long doctorId) {
        return patientDoctorMapper.selectByUserAndDoctor(userId, doctorId);
    }
    
    @Override
    public PageResult<User> getPatientsByDoctor(Long doctorId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<User> patients = patientDoctorMapper.selectPatientsByDoctorId(doctorId, offset, size);
        Long total = patientDoctorMapper.countPatientsByDoctorId(doctorId);
        return new PageResult<>(patients, total, page, size);
    }
    
    @Override
    public boolean savePatientDoctor(PatientDoctor patientDoctor) {
        return patientDoctorMapper.insert(patientDoctor) > 0;
    }
    
    @Override
    public boolean updatePatientDoctorStatus(Long id, Integer status) {
        return patientDoctorMapper.updateStatus(id, status) > 0;
    }
    
    @Override
    public boolean updatePatientDoctorStatusByUserAndDoctor(Long userId, Long doctorId, Integer status) {
        return patientDoctorMapper.updateStatusByUserAndDoctor(userId, doctorId, status) > 0;
    }
}