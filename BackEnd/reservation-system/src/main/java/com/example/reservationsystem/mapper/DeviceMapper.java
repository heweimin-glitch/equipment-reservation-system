package com.example.reservationsystem.mapper;
import com.example.reservationsystem.entity.Device;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface DeviceMapper {


    @Select("select * from Equipments")
    List<Device> findAll();

    @Select("select * from Equipments where id = #{id}")
    Device findById(String id);

    @Select("select number from Equipments where id = #{id}")
    Integer getNumber(String id);

    @Update("""
update Equipments
set number = number - 1
where id = #{id}
""")
    void reduceNumber(String id);


    @Update("""
update Equipments
set number = number + 1
where id = #{id}
""")
    void addNumber(String id);


    @Select("select * from Equipments where left(id,2)=#{prefix}")
    List<Device> findByPrefix(String prefix);


    @Update("update Equipments set number=number+#{num} where id=#{id}")
    void xxaddNumber(String id,int num);


    @Update("update Equipments set number=number-#{num} where id=#{id} and number>=#{num}")
    int xxreduceNumber(String id,int num);


    @Insert("""
        insert into Equipments
        values(#{id},#{name},#{introduction},#{address},#{ImageUrl},#{number},#{spec})

    """)
    void insert(Device d);


    @Select("select count(*) from Equipments where id=#{id}")
    int exists(String id);
}