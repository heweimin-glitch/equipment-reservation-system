package com.example.reservationsystem.mapper;
import com.example.reservationsystem.entity.Device;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import java.util.Map;

/**
 * 设备表（Equipments）MyBatis Mapper
 */
@Mapper
public interface DeviceMapper {

    /** 查询全部设备 */
    @Select("select * from Equipments")
    List<Device> findAll();

    /** 根据ID查询单个设备 */
    @Select("select * from Equipments where id = #{id}")
    Device findById(String id);

    /** 查询设备当前库存数量 */
    @Select("select number from Equipments where id = #{id}")
    Integer getNumber(String id);

    /** 库存 -1（审批通过时调用） */
    @Update("""
update Equipments
set number = number - 1
where id = #{id}
""")
    void reduceNumber(String id);

    /** 库存 +1（归还设备时调用） */
    @Update("""
update Equipments
set number = number + 1
where id = #{id}
""")
    void addNumber(String id);

    /** 按学科前缀查询设备（管理员管理同类别设备） */
    @Select("select * from Equipments where left(id,2)=#{prefix}")
    List<Device> findByPrefix(String prefix);

    /** 入库：增加指定数量 */
    @Update("update Equipments set number=number+#{num} where id=#{id}")
    void xxaddNumber(String id, int num);

    /** 出库：减少指定数量，库存不足时返回0 */
    @Update("update Equipments set number=number-#{num} where id=#{id} and number>=#{num}")
    int xxreduceNumber(String id, int num);

    /** 新增设备 */
    @Insert("""
        insert into Equipments
        values(#{id},#{name},#{introduction},#{address},#{ImageUrl},#{number},#{spec})
    """)
    void insert(Device d);

    /** 检查设备ID是否已存在 */
    @Select("select count(*) from Equipments where id=#{id}")
    int exists(String id);

    /** 查询所有库存>0的设备（AI空闲设备查询用） */
    @Select("SELECT name, number from Equipments where number > 0")
    List<Map<String, Object>> findFreeDevices();

    /** 按设备名称精确查询详情（AI设备详情查询用） */
    @Select("SELECT name, introduction, spec, address, number FROM Equipments WHERE name = #{name}")
    Map<String, Object> findDeviceDetail(String name);

}