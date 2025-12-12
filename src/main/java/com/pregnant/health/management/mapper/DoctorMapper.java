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
    
    @Insert("INSERT INTO doctors(user_id, name, hospital, department, title, specialty, introduction, score, consultation_count, positive_rate, avatar) " +
            "VALUES(#{userId}, #{name}, #{hospital}, #{department}, #{title}, #{specialty}, #{introduction}, #{score}, #{consultationCount}, #{positiveRate}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Doctor doctor);
    
    @Update("UPDATE doctors SET name=#{name}, hospital=#{hospital}, department=#{department}, title=#{title}, " +
            "specialty=#{specialty}, introduction=#{introduction}, score=#{score}, " +
            "consultation_count=#{consultationCount}, positive_rate=#{positiveRate}, avatar=#{avatar}, " +
            "updated_time=NOW() WHERE id=#{id}")
    int update(Doctor doctor);
    
    @Delete("DELETE FROM doctors WHERE id = #{id}")
    int deleteById(Long id);
    
    @Select("SELECT * FROM doctors WHERE user_id = #{userId}")
    Doctor selectByUserId(Long userId);
    
    // 按姓名查询医生列表
    @Select("SELECT d.*, u.username, u.real_name as realName FROM doctors d " +
            "LEFT JOIN users u ON d.user_id = u.id " +
            "WHERE u.real_name LIKE CONCAT('%', #{realName}, '%') " +
            "LIMIT #{offset}, #{limit}")
    List<Doctor> selectByRealName(@Param("realName") String realName, @Param("offset") Integer offset, @Param("limit") Integer limit);
    
    // 按姓名查询医生总数
    @Select("SELECT COUNT(*) FROM doctors d " +
            "LEFT JOIN users u ON d.user_id = u.id " +
            "WHERE u.real_name LIKE CONCAT('%', #{realName}, '%')")
    Long countByRealName(@Param("realName") String realName);
}