package com.pregnant.health.management.service.impl;

import com.pregnant.health.management.entity.Appointment;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.mapper.AppointmentMapper;
import com.pregnant.health.management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    
    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Override
    public Appointment getById(Long id) {
        return appointmentMapper.selectById(id);
    }
    
    @Override
    public PageResult<Appointment> getAppointmentList(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Appointment> appointments = appointmentMapper.selectAll(offset, size);
        Long total = appointmentMapper.countAll();
        return new PageResult<>(appointments, total, page, size);
    }
    
    @Override
    public PageResult<Appointment> getAppointmentListByUserRealName(String userRealName, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Appointment> appointments = appointmentMapper.selectByUserRealName(userRealName, offset, size);
        Long total = appointmentMapper.countByUserRealName(userRealName);
        return new PageResult<>(appointments, total, page, size);
    }
    
    @Override
    public PageResult<Appointment> getAppointmentListByDoctor(Long doctorId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Appointment> appointments = appointmentMapper.selectByDoctorId(doctorId, offset, size);
        Long total = appointmentMapper.countByDoctorId(doctorId);
        return new PageResult<>(appointments, total, page, size);
    }
    
    @Override
    public boolean saveAppointment(Appointment appointment) {
        return appointmentMapper.insert(appointment) > 0;
    }
    
    @Override
    public boolean updateAppointmentStatus(Long id, Integer status) {
        return appointmentMapper.updateStatus(id, status) > 0;
    }
    
    @Override
    public boolean deleteAppointment(Long id) {
        return appointmentMapper.deleteById(id) > 0;
    }
    
    @Override
    public List<Map<String, Object>> getAppointmentTrend() {
        return appointmentMapper.selectAppointmentTrend();
    }
    
    @Override
    public List<Map<String, Object>> getPatientStats() {
        return appointmentMapper.selectPatientStats();
    }
}