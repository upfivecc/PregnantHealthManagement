package com.pregnant.health.management.mapper;

import com.pregnant.health.management.entity.Knowledge;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KnowledgeMapper {
    
    @Select("SELECT * FROM knowledge WHERE id = #{id}")
    Knowledge selectById(Long id);
    
    @Select("SELECT * FROM knowledge ORDER BY created_time DESC LIMIT #{offset}, #{limit}")
    List<Knowledge> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM knowledge")
    Long countAll();
    
    @Insert("INSERT INTO knowledge(title, content, category, status, created_by) " +
            "VALUES(#{title}, #{content}, #{category}, #{status}, #{createdBy})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Knowledge knowledge);
    
    @Update("UPDATE knowledge SET title=#{title}, content=#{content}, category=#{category}, " +
            "status=#{status}, updated_time=NOW() WHERE id=#{id}")
    int update(Knowledge knowledge);
    
    @Delete("DELETE FROM knowledge WHERE id = #{id}")
    int deleteById(Long id);
    
    @Select("SELECT * FROM knowledge WHERE status = 1 ORDER BY created_time DESC LIMIT #{offset}, #{limit}")
    List<Knowledge> selectPublished(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM knowledge WHERE status = 1")
    Long countPublished();
}