package com.pregnant.health.management.mapper;

import com.pregnant.health.management.entity.Appointment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppointmentMapper {
    
    @Select("SELECT * FROM appointments WHERE id = #{id}")
    Appointment selectById(Long id);
    
    @Select("SELECT a.*, u.username as userName, d.hospital, d.department, d.title " +
            "FROM appointments a " +
            "LEFT JOIN users u ON a.user_id = u.id " +
            "LEFT JOIN doctors d ON a.doctor_id = d.id " +
            "ORDER BY a.appointment_time DESC " +
            "LIMIT #{offset}, #{limit}")
    List<Appointment> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM appointments")
    Long countAll();
    
    @Insert("INSERT INTO appointments(user_id, doctor_id, appointment_time, status, remark) " +
            "VALUES(#{userId}, #{doctorId}, #{appointmentTime}, #{status}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Appointment appointment);
    
    @Update("UPDATE appointments SET status=#{status}, updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    @Delete("DELETE FROM appointments WHERE id = #{id}")
    int deleteById(Long id);
}