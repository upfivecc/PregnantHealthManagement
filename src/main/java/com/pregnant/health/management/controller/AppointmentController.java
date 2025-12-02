package com.pregnant.health.management.controller;

import com.pregnant.health.management.entity.Appointment;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    @GetMapping("/{id}")
    public Result<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment != null) {
            return Result.success(appointment);
        } else {
            return Result.error("预约不存在");
        }
    }
    
    @GetMapping
    public Result<PageResult<Appointment>> getAppointmentList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageResult<Appointment> result = appointmentService.getAppointmentList(page, size);
        return Result.success(result);
    }
    
    @PostMapping
    public Result<String> saveAppointment(@RequestBody Appointment appointment) {
        boolean success = appointmentService.saveAppointment(appointment);
        if (success) {
            return Result.success("预约成功");
        } else {
            return Result.error("预约失败");
        }
    }
    
    @PutMapping("/{id}/status")
    public Result<String> updateAppointmentStatus(@PathVariable Long id, @RequestParam Integer status) {
        boolean success = appointmentService.updateAppointmentStatus(id, status);
        if (success) {
            return Result.success("状态更新成功");
        } else {
            return Result.error("状态更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteAppointment(@PathVariable Long id) {
        boolean success = appointmentService.deleteAppointment(id);
        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
}