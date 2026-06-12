package com.example.reservationsystem.controller;

import com.example.reservationsystem.service.ReservationService;
import com.example.reservationsystem.dto.IntentDTO;
import com.example.reservationsystem.mapper.DeviceMapper;
import com.example.reservationsystem.service.DeepSeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * AI 助手接口
 * 接收用户自然语言输入，通过 DeepSeek 解析意图后调用对应业务方法
 * 意图类型：DETAIL（设备详情）/ LIST（空闲设备）/ RECORD（预约记录）/ CHAT（普通对话）
 */
@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    private ReservationService reservationService;

    /**
     * POST /ai/chat —— AI 对话入口
     * 1. 调用 DeepSeek 将用户输入解析为结构化意图
     * 2. 根据意图分发到对应业务方法
     * @param body {message: 用户输入文本, userid: 当前用户ID}
     * @return 格式化后的文本回复
     */
    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, Object> body) {
        String msg = (String) body.get("message");
        String userid = (String) body.get("userid");

        // 1. AI 解析意图
        String aiJson = deepSeekService.parseIntent(msg);
        IntentDTO dto;
        try {
            ObjectMapper mapper = new ObjectMapper();
            dto = mapper.readValue(aiJson, IntentDTO.class);
        } catch (Exception e) {
            return "解析失败" + aiJson;
        }

        // 2. 根据意图执行对应操作
        if ("DETAIL".equals(dto.intent)) {
            String deviceName = dto.deviceName;
            if (deviceName == null || deviceName.isEmpty()) {
                return "未识别设备名称";
            }
            Map<String, Object> d = deviceMapper.findDeviceDetail(deviceName);
            if (d == null) {
                return "未找到该设备：" + deviceName;
            }
            return buildDetailText(d);
        }

        if ("LIST".equals(dto.intent)) {
            List<Map<String, Object>> list = deviceMapper.findFreeDevices();
            StringBuilder sb = new StringBuilder();
            sb.append("🔬当前可预约设备\n");
            sb.append("━━━━━━━━━━━━━\n\n");
            for (Map<String, Object> d : list) {
                sb.append("设备名称：").append(d.get("name")).append("\n")
                        .append("剩余数量：").append(d.get("number")).append("\n")
                        .append("─────────────\n");
            }
            return sb.toString();
        }

        if ("RECORD".equals(dto.intent)) {
            List<Map<String, Object>> records = reservationService.findrecord(userid);
            return buildRecordText(records);
        }

        if ("CHAT".equals(dto.intent)) {
            return deepSeekService.chat(body);
        }

        return "系统无法处理当前请求";
    }

    /**
     * 组装设备详情文本（含名称、介绍、规格、位置、数量）
     */
    private String buildDetailText(Map<String, Object> d) {
        return "📟 设备详情\n\n" +
                "名称：" + d.get("name") + "\n\n" +
                "📖 介绍：" + d.get("introduction") + "\n\n" +
                "⚙️ 规格：" + d.get("spec") + "\n\n" +
                "📍 存放位置：" + d.get("address") + "\n\n" +
                "📦 剩余数量：" + d.get("number");
    }

    /**
     * 组装预约记录文本（含设备名、申请人、审批人、时间、状态）
     */
    private String buildRecordText(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return "暂无预约记录";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("📋 我的预约记录\n");
        sb.append("━━━━━━━━━━━━━━\n\n");
        for (Map<String, Object> r : list) {
            sb.append("📟 设备名称：").append(r.get("deviceName")).append("\n");
            sb.append("👤 申请人：").append(r.get("applyName")).append("\n");
            sb.append("🧑‍💼 审批人：").append(r.get("adminName")).append("\n");
            sb.append("🕒 开始时间：").append(r.get("start_time")).append("\n");
            sb.append("🕒 结束时间：").append(r.get("end_time")).append("\n");
            sb.append("📌 状态：")
                    .append(convertStatus(Integer.parseInt(String.valueOf(r.get("status")))))
                    .append("\n");
            sb.append("━━━━━━━━━━━━━━\n\n");
        }
        return sb.toString();
    }

    /** 预约状态码 → 中文文本 */
    private String convertStatus(int status) {
        switch (status) {
            case 0: return "待审核";
            case 1: return "未通过";
            case 2: return "已取消";
            case 3: return "使用中";
            case 4: return "已完成";
            default: return "未知状态";
        }
    }
}