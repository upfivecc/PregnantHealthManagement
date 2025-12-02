package com.pregnant.health.management.service.impl;

import com.pregnant.health.management.entity.Appointment;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.mapper.AppointmentMapper;
import com.pregnant.health.management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}