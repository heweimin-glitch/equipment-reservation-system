package com.example.reservationsystem.service;

import com.example.reservationsystem.entity.Reservation;
import com.example.reservationsystem.mapper.DeviceMapper;
import com.example.reservationsystem.mapper.ReservationMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 预约业务逻辑层
 * 处理预约查询、审批、取消、归还等核心业务流程
 */
@Service
public class ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    /** 普通用户——查询当前有效预约（待审核/未通过/使用中） */
    public List<Map<String, Object>> findCurrent(String applyId) {
        return reservationMapper.findCurrent(applyId);
    }

    /** 管理员——查询当前待处理预约（待审核/已取消） */
    public List<Map<String, Object>> findAdminCurrent(String applyId) {
        return reservationMapper.findAdminCurrent(applyId);
    }

    /** 用户取消预约 → 状态改为"已取消" */
    public void cancel(Integer id) {
        reservationMapper.cancel(id);
    }

    /**
     * 归还设备
     * 1. 查询预约关联的设备ID
     * 2. 设备库存 +1
     * 3. 预约状态改为"已完成"
     */
    public void finish(Integer id) {
        String deviceId = reservationMapper.getDeviceId(id);
        deviceMapper.addNumber(deviceId);
        reservationMapper.finish(id);
    }

    /**
     * 管理员审批通过
     * 1. 查询关联设备ID
     * 2. 检查库存是否充足
     * 3. 库存 -1
     * 4. 状态改为"使用中"
     * @return 库存充足时返回 true，库存不足返回 false
     */
    public boolean approve(Integer id) {
        String deviceId = reservationMapper.getDeviceId(id);
        Integer number = deviceMapper.getNumber(deviceId);

        if (number <= 0) {
            return false; // 库存不足，审批失败
        }

        deviceMapper.reduceNumber(deviceId);
        reservationMapper.approve(id);
        return true;
    }

    /** 管理员拒绝预约 → 状态改为"未通过"，记录拒绝原因 */
    public void reject(Integer id, String rejectReason) {
        reservationMapper.reject(id, rejectReason);
    }

    /** 普通用户——查询历史预约（已取消/已完成） */
    public List<Map<String, Object>> findUserHistory(String applyId) {
        return reservationMapper.findUserHistory(applyId);
    }

    /** AI助手——查询用户全部预约记录 */
    public List<Map<String, Object>> findrecord(String applyId) {
        return reservationMapper.findrecord(applyId);
    }

    /** 管理员——查询历史预约（未通过/使用中/已完成） */
    public List<Map<String, Object>> findAdminHistory(String applyId) {
        return reservationMapper.findAdminHistory(applyId);
    }

    /**
     * 新增预约
     * @param r 预约对象（status 会被强制设为 0=待审核）
     * @return 插入成功返回 true
     */
    public boolean add(Reservation r) {
        r.setStatus(0); // 新预约初始状态：待审核
        return reservationMapper.insert(r) > 0;
    }

}