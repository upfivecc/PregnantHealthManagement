package com.pregnant.health.management.mapper;

import com.pregnant.health.management.entity.PregnancyRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PregnancyRecordMapper {
    
    @Select("SELECT * FROM pregnancy_records WHERE id = #{id}")
    PregnancyRecord selectById(Long id);
    
    @Select("SELECT * FROM pregnancy_records WHERE user_id = #{userId} ORDER BY record_date DESC")
    List<PregnancyRecord> selectByUserId(Long userId);
    
    @Select("SELECT * FROM pregnancy_records WHERE user_id = #{userId} ORDER BY record_date DESC LIMIT #{offset}, #{limit}")
    List<PregnancyRecord> selectByUserIdWithPage(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM pregnancy_records WHERE user_id = #{userId}")
    Long countByUserId(Long userId);
    
    @Insert("INSERT INTO pregnancy_records(user_id, height, weight, blood_pressure_high, blood_pressure_low, " +
            "fetal_movement_count, record_date, remark) " +
            "VALUES(#{userId}, #{height}, #{weight}, #{bloodPressureHigh}, #{bloodPressureLow}, " +
            "#{fetalMovementCount}, #{recordDate}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PregnancyRecord record);
    
    @Update("UPDATE pregnancy_records SET height=#{height}, weight=#{weight}, blood_pressure_high=#{bloodPressureHigh}, " +
            "blood_pressure_low=#{bloodPressureLow}, fetal_movement_count=#{fetalMovementCount}, " +
            "record_date=#{recordDate}, remark=#{remark}, updated_time=NOW() WHERE id=#{id}")
    int update(PregnancyRecord record);
    
    @Delete("DELETE FROM pregnancy_records WHERE id = #{id}")
    int deleteById(Long id);
}