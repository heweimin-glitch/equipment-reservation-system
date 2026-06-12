package com.example.reservationsystem.mapper;

import com.example.reservationsystem.entity.Device;
import com.example.reservationsystem.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户表（Users）MyBatis Mapper
 */
@Mapper
public interface UserMapper {

    /** 根据ID查询用户 */
    @Select("select * from Users where id = #{id}")
    User findById(String id);

    /** 修改密码 */
    @Update("update Users set password=#{newPwd} where id=#{id}")
    void updatePassword(String id, String newPwd);

    /** 按ID前缀（前2位）查询该学科下的管理员列表 */
    @Select("""
SELECT * FROM Users
WHERE LEFT(id,2) = #{prefix}
AND is_admin = 1
""")
    List<User> getAdminsByPrefix(String prefix);

    /** 检查用户ID是否已存在（用于注册去重） */
    @Select("select count(*) from Users where id=#{id}")
    int exists(String id);

    /** 新增用户 */
    @Insert("""
insert into Users(id, name, password, is_admin)
values(#{id}, #{name}, #{password}, #{is_admin})
""")
    void insert(User u);
}