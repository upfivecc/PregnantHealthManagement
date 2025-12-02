package com.pregnant.health.management.mapper;

import com.pregnant.health.management.entity.Consultation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConsultationMapper {
    
    @Select("SELECT * FROM consultations WHERE id = #{id}")
    Consultation selectById(Long id);
    
    @Select("SELECT c.*, u.username as userName, d.hospital, d.department, d.title " +
            "FROM consultations c " +
            "LEFT JOIN users u ON c.user_id = u.id " +
            "LEFT JOIN doctors d ON c.doctor_id = d.id " +
            "ORDER BY c.created_time DESC " +
            "LIMIT #{offset}, #{limit}")
    List<Consultation> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM consultations")
    Long countAll();
    
    @Select("SELECT c.*, u.username as userName " +
            "FROM consultations c " +
            "LEFT JOIN users u ON c.user_id = u.id " +
            "WHERE c.doctor_id = #{doctorId} " +
            "ORDER BY c.created_time DESC " +
            "LIMIT #{offset}, #{limit}")
    List<Consultation> selectByDoctorId(@Param("doctorId") Long doctorId, @Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM consultations WHERE doctor_id = #{doctorId}")
    Long countByDoctorId(Long doctorId);
    
    @Select("SELECT COUNT(*) FROM consultations WHERE status = 0")
    Long countUnreplied();
    
    @Insert("INSERT INTO consultations(user_id, doctor_id, title, content, status) " +
            "VALUES(#{userId}, #{doctorId}, #{title}, #{content}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Consultation consultation);
    
    @Update("UPDATE consultations SET reply=#{reply}, status=1, replied_time=NOW(), updated_time=NOW() WHERE id=#{id}")
    int reply(@Param("id") Long id, @Param("reply") String reply);
    
    @Delete("DELETE FROM consultations WHERE id = #{id}")
    int deleteById(Long id);
}