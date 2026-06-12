package com.example.reservationsystem.mapper;
import com.example.reservationsystem.entity.Reservation;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 预约表（Reservations）MyBatis Mapper
 * 状态码：0=待审核，1=未通过，2=已取消，3=使用中，4=已完成
 */
@Mapper
public interface ReservationMapper {

    /** 查询某用户的所有预约记录（AI助手用） */
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
WHERE r.apply_id=#{userid}
            """)
    List<Map<String,Object>> findrecord(String applyId);

    /** 普通用户——当前预约（状态：待审核/未通过/使用中） */
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

    /** 管理员——当前待审批预约（状态：待审核/已取消） */
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

    /** 取消预约：设置状态为"已取消" */
    @Update("update Reservations set status = 2 where id = #{id}")
    void cancel(Integer id);

    /** 归还设备：设置状态为"已完成" */
    @Update("update Reservations set status = 4 where id = #{id}")
    void finish(Integer id);

    /** 审批通过：设置状态为"使用中" */
    @Update("update Reservations set status = 3 where id = #{id}")
    void approve(Integer id);

    /** 审批拒绝：设置状态为"未通过"，同时记录拒绝原因 */
    @Update("""
update Reservations
set status = 1,
reject_reason = #{rejectReason}
where id = #{id}
""")
    void reject(Integer id, String rejectReason);

    /** 根据预约ID查询对应的设备ID（用于归还/审批时操作库存） */
    @Select("""
select device_id
from Reservations
where id = #{id}
""")
    String getDeviceId(Integer id);

    /** 普通用户——历史预约（状态：已取消/已完成） */
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

    /** 管理员——历史预约（状态：未通过/使用中/已完成） */
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

    /** 新增预约记录 */
    @Insert("""
INSERT INTO Reservations
(id, apply_id, admin_id, device_id, start_time, end_time, purpose, status)
VALUES
(#{id}, #{apply_id}, #{admin_id}, #{device_id}, #{start_time}, #{end_time}, #{purpose}, #{status})
""")
    int insert(Reservation r);
}

