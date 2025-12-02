package com.pregnant.health.management.mapper;

import com.pregnant.health.management.entity.Appointment;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface AppointmentMapper {
    
    @Select("SELECT * FROM appointments WHERE id = #{id}")
    Appointment selectById(Long id);
    
    @Select("SELECT a.*, u.username as userName, u.real_name as userRealName, d.hospital, d.department, d.title " +
            "FROM appointments a " +
            "LEFT JOIN users u ON a.user_id = u.id " +
            "LEFT JOIN doctors d ON a.doctor_id = d.id " +
            "ORDER BY a.appointment_time DESC " +
            "LIMIT #{offset}, #{limit}")
    List<Appointment> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM appointments")
    Long countAll();
    
    // 按用户姓名查询预约列表
    @Select("SELECT a.*, u.username as userName, u.real_name as userRealName, d.hospital, d.department, d.title " +
            "FROM appointments a " +
            "LEFT JOIN users u ON a.user_id = u.id " +
            "LEFT JOIN doctors d ON a.doctor_id = d.id " +
            "WHERE u.real_name LIKE CONCAT('%', #{userRealName}, '%') " +
            "ORDER BY a.appointment_time DESC " +
            "LIMIT #{offset}, #{limit}")
    List<Appointment> selectByUserRealName(@Param("userRealName") String userRealName, @Param("offset") Integer offset, @Param("limit") Integer limit);
    
    // 按用户姓名查询预约总数
    @Select("SELECT COUNT(*) FROM appointments a " +
            "LEFT JOIN users u ON a.user_id = u.id " +
            "WHERE u.real_name LIKE CONCAT('%', #{userRealName}, '%')")
    Long countByUserRealName(@Param("userRealName") String userRealName);
    
    // 查询预约趋势数据（按日期统计）
    @Select("SELECT DATE(appointment_time) as date, COUNT(*) as count " +
            "FROM appointments " +
            "WHERE appointment_time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY DATE(appointment_time) " +
            "ORDER BY date")
    List<Map<String, Object>> selectAppointmentTrend();
    
    // 查询病人数量统计（按日期统计）
    @Select("SELECT DATE(created_time) as date, COUNT(DISTINCT user_id) as count " +
            "FROM appointments " +
            "WHERE created_time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY DATE(created_time) " +
            "ORDER BY date")
    List<Map<String, Object>> selectPatientStats();
    
    @Insert("INSERT INTO appointments(user_id, doctor_id, appointment_time, status, remark) " +
            "VALUES(#{userId}, #{doctorId}, #{appointmentTime}, #{status}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Appointment appointment);
    
    @Update("UPDATE appointments SET status=#{status}, updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    @Delete("DELETE FROM appointments WHERE id = #{id}")
    int deleteById(Long id);
}