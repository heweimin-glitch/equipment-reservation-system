package com.example.reservationsystem.controller;

import com.example.reservationsystem.entity.Reservation;
import com.example.reservationsystem.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 预约接口
 * 提供预约查询、新增、审批、取消、归还等 REST API
 * 所有接口根据 isAdmin 字段自动区分管理员/普通用户的查询范围
 */
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * GET /reservation/current?applyId=xxx&isAdmin=0
     * 查询当前有效预约：管理员看待处理，普通用户看自己的
     */
    @GetMapping("/current")
    public List<Map<String, Object>> current(String applyId, Integer isAdmin) {
        if (isAdmin == 1) {
            return reservationService.findAdminCurrent(applyId);
        }
        return reservationService.findCurrent(applyId);
    }

    /**
     * GET /reservation/history?applyId=xxx&isAdmin=0
     * 查询历史预约：管理员看已处理，普通用户看自己的历史
     */
    @GetMapping("/history")
    public List<Map<String, Object>> history(String applyId, Integer isAdmin) {
        if (isAdmin == 1) {
            return reservationService.findAdminHistory(applyId);
        }
        return reservationService.findUserHistory(applyId);
    }

    /** POST /reservation/cancel —— 用户取消预约，状态改为"已取消" */
    @PostMapping("/cancel")
    public void cancel(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        reservationService.cancel(id);
    }

    /** POST /reservation/finish —— 归还设备，库存+1，状态改为"已完成" */
    @PostMapping("/finish")
    public void finish(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        reservationService.finish(id);
    }

    /**
     * POST /reservation/approve —— 管理员审批通过
     * @return 库存充足返回 true，库存不足返回 false
     */
    @PostMapping("/approve")
    public boolean approve(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        return reservationService.approve(id);
    }

    /** POST /reservation/reject —— 管理员拒绝预约，需填写拒绝原因 */
    @PostMapping("/reject")
    public void reject(@RequestBody Map<String, Object> map) {
        Integer id = Integer.parseInt(map.get("id").toString());
        String rejectReason = map.get("rejectReason").toString();
        reservationService.reject(id, rejectReason);
    }

    /** POST /reservation/add —— 用户新增预约，初始状态为"待审核" */
    @PostMapping("/add")
    public boolean add(@RequestBody Reservation r) {
        System.out.println("收到预约：" + r);
        return reservationService.add(r);
    }

}