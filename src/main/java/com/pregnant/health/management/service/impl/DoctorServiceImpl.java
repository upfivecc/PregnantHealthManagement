package com.pregnant.health.management.service.impl;

import com.pregnant.health.management.entity.Doctor;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.mapper.DoctorMapper;
import com.pregnant.health.management.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Override
    public Doctor getById(Long id) {
        return doctorMapper.selectById(id);
    }
    
    @Override
    public Doctor getByUserId(Long userId) {
        return doctorMapper.selectByUserId(userId);
    }
    
    @Override
    public PageResult<Doctor> getDoctorList(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Doctor> doctors = doctorMapper.selectAll(offset, size);
        Long total = doctorMapper.countAll();
        return new PageResult<>(doctors, total, page, size);
    }
    
    @Override
    public PageResult<Doctor> getDoctorListByName(String realName, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Doctor> doctors = doctorMapper.selectByRealName(realName, offset, size);
        Long total = doctorMapper.countByRealName(realName);
        return new PageResult<>(doctors, total, page, size);
    }
    
    @Override
    public boolean saveDoctor(Doctor doctor) {
        return doctorMapper.insert(doctor) > 0;
    }
    
    @Override
    public boolean updateDoctor(Doctor doctor) {
        return doctorMapper.update(doctor) > 0;
    }
    
    @Override
    public boolean deleteDoctor(Long id) {
        return doctorMapper.deleteById(id) > 0;
    }
}