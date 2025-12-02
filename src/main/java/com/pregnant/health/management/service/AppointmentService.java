package com.pregnant.health.management.service;

import com.pregnant.health.management.entity.Appointment;
import com.pregnant.health.management.entity.PageResult;

public interface AppointmentService {
    
    Appointment getById(Long id);
    
    PageResult<Appointment> getAppointmentList(Integer page, Integer size);
    
    // 按用户姓名查询预约列表
    PageResult<Appointment> getAppointmentListByUserRealName(String userRealName, Integer page, Integer size);
    
    boolean saveAppointment(Appointment appointment);
    
    boolean updateAppointmentStatus(Long id, Integer status);
    
    boolean deleteAppointment(Long id);
}