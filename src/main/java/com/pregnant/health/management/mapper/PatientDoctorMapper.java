package com.pregnant.health.management.mapper;

import com.pregnant.health.management.entity.PatientDoctor;
import com.pregnant.health.management.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PatientDoctorMapper {
    
    @Select("SELECT * FROM patient_doctor WHERE id = #{id}")
    PatientDoctor selectById(Long id);
    
    @Select("SELECT * FROM patient_doctor WHERE user_id = #{userId} AND doctor_id = #{doctorId}")
    PatientDoctor selectByUserAndDoctor(@Param("userId") Long userId, @Param("doctorId") Long doctorId);
    
    @Select("SELECT u.* FROM users u " +
            "LEFT JOIN patient_doctor pd ON u.id = pd.user_id " +
            "WHERE pd.doctor_id = #{doctorId} AND pd.status = 1 " +
            "LIMIT #{offset}, #{limit}")
    List<User> selectPatientsByDoctorId(@Param("doctorId") Long doctorId, @Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM patient_doctor WHERE doctor_id = #{doctorId} AND status = 1")
    Long countPatientsByDoctorId(Long doctorId);
    
    @Insert("INSERT INTO patient_doctor(user_id, doctor_id, status) " +
            "VALUES(#{userId}, #{doctorId}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PatientDoctor patientDoctor);
    
    @Update("UPDATE patient_doctor SET status=#{status}, updated_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    @Update("UPDATE patient_doctor SET status=#{status}, updated_time=NOW() WHERE user_id=#{userId} AND doctor_id=#{doctorId}")
    int updateStatusByUserAndDoctor(@Param("userId") Long userId, @Param("doctorId") Long doctorId, @Param("status") Integer status);
}