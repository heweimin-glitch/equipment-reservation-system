package com.example.reservationsystem.mapper;

import com.example.reservationsystem.entity.Device;
import com.example.reservationsystem.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from Users where id = #{id}")
    User findById(String id);

    @Update("update Users set password=#{newPwd} where id=#{id}")
    void updatePassword(String id,String newPwd);

    @Select("""
SELECT * FROM Users
WHERE LEFT(id,2) = #{prefix}
AND is_admin = 1
""")
    List<User> getAdminsByPrefix(String prefix);


    @Select("select count(*) from Users where id=#{id}")
    int exists(String id);

    @Insert("""
insert into Users(id, name, password,is_admin)
values(#{id}, #{name}, #{password},#{is_admin})
""")
    void insert(User u);
}