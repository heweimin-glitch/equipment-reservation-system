package com.example.reservationsystem.entity;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 预约实体
 * 对应数据库 Reservations 表。
 * 状态码：0=待审核，1=未通过，2=已取消，3=使用中，4=已完成
 */
@Data
public class Reservation {
    /** 预约ID（8位随机数） */
    private String id;
    /** 申请人ID（外键 → Users.id） */
    private String apply_id;
    /** 审批人ID（外键 → Users.id） */
    private String admin_id;
    /** 设备ID（外键 → Equipments.id） */
    private String device_id;
    /** 预约开始时间 */
    private LocalDateTime start_time;
    /** 预约结束时间 */
    private LocalDateTime end_time;
    /** 预约用途说明 */
    private String purpose;
    /** 预约状态：0=待审核 1=未通过 2=已取消 3=使用中 4=已完成 */
    private int status;
    /** 拒绝原因（status=1时填写） */
    private String reject_reason;
}
