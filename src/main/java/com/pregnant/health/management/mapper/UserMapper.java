package com.pregnant.health.management.mapper;

import com.pregnant.health.management.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectById(Long id);
    
    @Select("SELECT * FROM users WHERE username = #{username}")
    User selectByUsername(String username);
    
    @Select("SELECT * FROM users WHERE username = #{username} OR phone = #{username}")
    User selectByUsernameOrPhone(String username);
    
    @Select("SELECT * FROM users WHERE role = #{role} LIMIT #{offset}, #{limit}")
    List<User> selectByRole(@Param("role") String role, @Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM users WHERE role = #{role}")
    Long countByRole(String role);
    
    @Insert("INSERT INTO users(username, password, email, phone, real_name, gender, age, role, status) " +
            "VALUES(#{username}, #{password}, #{email}, #{phone}, #{realName}, #{gender}, #{age}, #{role}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    @Update("UPDATE users SET username=#{username}, email=#{email}, phone=#{phone}, real_name=#{realName}, " +
            "gender=#{gender}, age=#{age}, role=#{role}, status=#{status}, updated_time=NOW() WHERE id=#{id}")
    int update(User user);
    
    @Update("UPDATE users SET password=#{password} WHERE id=#{id}")
    int updatePassword(@Param("id") Long id, @Param("password") String password);
    
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Long id);
    
    @Select("SELECT * FROM users LIMIT #{offset}, #{limit}")
    List<User> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    @Select("SELECT COUNT(*) FROM users")
    Long countAll();
}