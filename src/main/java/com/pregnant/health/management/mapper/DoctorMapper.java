package com.pregnant.health.management.mapper;

import com.pregnant.health.management.entity.Doctor;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DoctorMapper {
    
    @Select("SELECT * FROM doctors WHERE id = #{id}")
    Doctor selectById(Long id);
    
    @Select("SELECT d.*, u.username, u.real_name as realName FROM doctors d " +
            "LEFT JOIN users u ON d.user_id = u.id " +
            "LIMIT #{offset}, #{limit}")
    List<Doctor> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM doctors")
    Long countAll();
    
    @Insert("INSERT INTO doctors(user_id, hospital, department, title, specialty, introduction) " +
            "VALUES(#{userId}, #{hospital}, #{department}, #{title}, #{specialty}, #{introduction})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Doctor doctor);
    
    @Update("UPDATE doctors SET hospital=#{hospital}, department=#{department}, title=#{title}, " +
            "specialty=#{specialty}, introduction=#{introduction}, updated_time=NOW() WHERE id=#{id}")
    int update(Doctor doctor);
    
    @Delete("DELETE FROM doctors WHERE id = #{id}")
    int deleteById(Long id);
    
    @Select("SELECT * FROM doctors WHERE user_id = #{userId}")
    Doctor selectByUserId(Long userId);
}