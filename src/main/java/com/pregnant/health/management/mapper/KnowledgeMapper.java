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
    
    // 按标题模糊查询知识列表
    @Select("SELECT * FROM knowledge WHERE title LIKE CONCAT('%', #{title}, '%') ORDER BY created_time DESC LIMIT #{offset}, #{limit}")
    List<Knowledge> selectByTitle(@Param("title") String title, @Param("offset") Integer offset, @Param("limit") Integer limit);
    
    // 按标题模糊查询知识总数
    @Select("SELECT COUNT(*) FROM knowledge WHERE title LIKE CONCAT('%', #{title}, '%')")
    Long countByTitle(@Param("title") String title);
    
    // 复合条件查询知识列表
    @Select("<script>" +
            "SELECT * FROM knowledge " +
            "<where>" +
            "  <if test='title != null and title != \"\"'> AND title LIKE CONCAT('%', #{title}, '%')</if>" +
            "  <if test='category != null and category != \"\"'> AND category = #{category}</if>" +
            "  <if test='status != null'> AND status = #{status}</if>" +
            "</where>" +
            " ORDER BY created_time DESC LIMIT #{offset}, #{limit}" +
            "</script>")
    List<Knowledge> selectByConditions(@Param("title") String title, @Param("category") String category, 
                                     @Param("status") Integer status, @Param("offset") Integer offset, 
                                     @Param("limit") Integer limit);
    
    // 复合条件查询知识总数
    @Select("<script>" +
            "SELECT COUNT(*) FROM knowledge " +
            "<where>" +
            "  <if test='title != null and title != \"\"'> AND title LIKE CONCAT('%', #{title}, '%')</if>" +
            "  <if test='category != null and category != \"\"'> AND category = #{category}</if>" +
            "  <if test='status != null'> AND status = #{status}</if>" +
            "</where>" +
            "</script>")
    Long countByConditions(@Param("title") String title, @Param("category") String category, 
                          @Param("status") Integer status);
    
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