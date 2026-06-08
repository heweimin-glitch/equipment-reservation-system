package com.example.reservationsystem.mapper;
import com.example.reservationsystem.entity.Reservation;
import com.example.reservationsystem.vo.ReservationVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationMapper {
//0待审查，1未通过，2已取消，3使用中，4已完成

    //普通用户获取当前预约界面数据
    @Select("""
SELECT
    r.id,
    r.apply_id,
    r.admin_id,
    r.device_id,
    r.start_time,
    r.end_time,
    r.purpose,
    r.status,
    r.reject_reason,
    e.name AS deviceName,
    e.address,
    u1.name AS applyName,
    u2.name AS adminName
FROM Reservations r
LEFT JOIN Equipments e
ON r.device_id = e.id
LEFT JOIN Users u1
ON r.apply_id = u1.id
LEFT JOIN Users u2
ON r.admin_id = u2.id
WHERE r.status IN (0,1,3) 
AND r.apply_id=#{applyId}
""")
    List<Map<String, Object>> findCurrent(String applyId);
//    @Select("""
//SELECT
//r.*,
//e.name AS deviceName,
//e.address
//
//FROM Reservations r
//
//JOIN Equipments e
//ON r.device_id = e.id
//
//WHERE r.apply_id = #{applyId}
//AND r.status IN (0,1,3)
//""")
//    List<Map<String, Object>> findCurrent(String applyId);
//    List<ReservationVO> findCurrent(String applyId);


//    @Results({
//            @Result(property = "applyName", column = "applyName"),
//            @Result(property = "adminName", column = "adminName"),
//            @Result(property = "deviceName", column = "deviceName"),
//            @Result(property = "address", column = "address")
//    })

    //管理员获取当前预约界面数据
    @Select("""
SELECT
    r.id,
    r.apply_id,
    r.admin_id,
    r.device_id,
    r.start_time,
    r.end_time,
    r.purpose,
    r.status,
    r.reject_reason,

    e.name AS deviceName,
    e.address,

    u1.name AS applyName,
    u2.name AS adminName

FROM Reservations r

LEFT JOIN Equipments e
ON r.device_id = e.id

LEFT JOIN Users u1
ON r.apply_id = u1.id

LEFT JOIN Users u2
ON r.admin_id = u2.id
    
WHERE r.status IN (0,2) 
AND r.admin_id=#{applyId}
""")
    List<Map<String, Object>> findAdminCurrent(String applyId);

//    List<ReservationVO> findAdminCurrent();



//    @Select("""
//SELECT
//r.*,
//e.name AS deviceName,
//e.address
//FROM Reservations r
//JOIN Equipments e
//ON r.device_id = e.id
//WHERE r.status IN (0,2)
//""")
//    List<ReservationVO> findAdminCurrent();

    @Update("update Reservations set status = 2 where id = #{id}")
    void cancel(Integer id);

    @Update("update Reservations set status = 4 where id = #{id}")
    void finish(Integer id);


    @Update("update Reservations set status = 3 where id = #{id}")
    void approve(Integer id);


    @Update("""
update Reservations
set status = 1,
reject_reason = #{rejectReason}
where id = #{id}
""")
    void reject(Integer id,String rejectReason);


    @Select("""
select device_id
from Reservations
where id = #{id}
""")
    String getDeviceId(Integer id);


    // 普通用户历史预约
    @Select("""
SELECT
    r.id,
    r.apply_id,
    r.admin_id,
    r.device_id,
    r.start_time,
    r.end_time,
    r.purpose,
    r.status,
    r.reject_reason,
    e.name AS deviceName,
    e.address,
    u1.name AS applyName,
    u2.name AS adminName
FROM Reservations r
LEFT JOIN Equipments e
ON r.device_id = e.id
LEFT JOIN Users u1
ON r.apply_id = u1.id
LEFT JOIN Users u2
ON r.admin_id = u2.id
WHERE r.status IN (2,4) 
AND r.apply_id=#{applyId}
""")
    List<Map<String, Object>> findUserHistory(String applyId);

//    @Select("""
//SELECT
//r.*,
//e.name AS deviceName,
//e.address
//FROM Reservations r
//JOIN Equipments e
//ON r.device_id = e.id
//WHERE r.apply_id = #{applyId}
//AND r.status IN (2,4)
//ORDER BY r.start_time DESC
//""")
//    List<ReservationVO> findUserHistory(String applyId);

    // 管理员历史预约
    @Select("""
SELECT
    r.id,
    r.apply_id,
    r.admin_id,
    r.device_id,
    r.start_time,
    r.end_time,
    r.purpose,
    r.status,
    r.reject_reason,

    e.name AS deviceName,
    e.address,

    u1.name AS applyName,
    u2.name AS adminName

FROM Reservations r

LEFT JOIN Equipments e
ON r.device_id = e.id

LEFT JOIN Users u1
ON r.apply_id = u1.id

LEFT JOIN Users u2
ON r.admin_id = u2.id
    
WHERE r.status IN (1,3,4) 
AND r.admin_id=#{applyId}
""")
    List<Map<String, Object>> findAdminHistory(String applyId);

//    @Select("""
//SELECT
//r.*,
//e.name AS deviceName,
//e.address
//FROM Reservations r
//JOIN Equipments e
//ON r.device_id = e.id
//WHERE r.status IN (1,3,4)
//ORDER BY r.start_time DESC
//""")
//    List<ReservationVO> findAdminHistory();

    @Insert("""
INSERT INTO Reservations
(id, apply_id, admin_id, device_id, start_time, end_time, purpose, status)
VALUES
(#{id}, #{apply_id}, #{admin_id}, #{device_id}, #{start_time}, #{end_time}, #{purpose}, #{status})
""")
    int insert(Reservation r);
}

