package com.example.reservationsystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * DeepSeek AI 服务集成
 * 提供两类能力：
 * 1. chat()    —— 普通对话（回答实验设备相关问题）
 * 2. parseIntent() —— 意图解析（将用户自然语言转为结构化意图 JSON）
 */
@Service
public class DeepSeekService {

    @Value("${deepseek.api-key}")
    private String apiKey;

    /**
     * 通用聊天接口
     * 会在请求前插入系统提示词，限定 AI 回答范围为实验设备预约相关内容
     * @param requestData 包含 messages 列表的请求数据
     * @return AI 回复文本
     */
    public String chat(Map<String, Object> requestData) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.deepseek.com/chat/completions";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            List<Map<String, String>> messages =
                    (List<Map<String, String>>) requestData.get("messages");

            if (messages == null) {
                messages = new ArrayList<>();
            }

            // =========================
            // system prompt（简化版）
            // =========================
            Map<String, String> system = new HashMap<>();
            system.put("role", "system");
            system.put("content",
                    "你是实验设备预约系统的AI助手。"
                            + "你负责回答实验设备、实验室使用和预约流程相关问题。"

                            + "系统预约流程如下："
                            + "1. 用户进入首页查看设备列表；"
                            + "2. 点击设备查看设备详情；"
                            + "3. 确认设备信息后进入预约页面；"
                            + "4. 填写预约时间和预约原因；"
                            + "5. 提交预约申请等待审核。"
                            + "如果用户询问如何预约设备，请按照上述流程回答。"
                            + "回答要求："
                            + "简洁、准确、友好，不要编造系统不存在的功能。"

                            + "注意："
                            + "为了保证系统数据安全，你不能代替用户执行预约、审批、删除、新增等数据库操作。"
                            + "当用户要求你帮忙预约设备时，请礼貌告知："
                            + "你无法直接进行预约操作，"
                            + "但可以帮助查询设备信息，并指导用户按照预约流程完成预约。"
                            + "回答要求："
                            + "简洁、准确、友好，不编造系统不存在的功能。"
            );

            messages.add(0, system);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "deepseek-v4-pro");
            body.put("messages", messages);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response.getBody());

            return root.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText()
                    .trim();

        } catch (Exception e) {
            return "AI服务异常：" + e.getMessage();
        }
    }

    /**
     * 意图解析接口
     * 调用 DeepSeek 将用户自然语言分类为结构化意图（DETAIL/LIST/RECORD/CHAT）
     * @param msg 用户输入的自然语言
     * @return JSON 字符串，包含 intent 和 deviceName 字段
     */
    public String parseIntent(String msg) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.deepseek.com/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            List<Map<String, String>> messages = new ArrayList<>();

            Map<String, String> system = new HashMap<>();
            system.put("role", "system");
            system.put("content",
                    "你是一个意图解析器，只负责分类用户意图。\n" +
                    "必须严格只输出JSON，不允许任何解释。\n\n" +
                    "JSON格式如下：\n" +
                    "{\n" +
                    "  \"intent\": \"DETAIL | LIST | RECORD | CHAT\",\n" +
                    "  \"deviceName\": \"如果有设备名就写，没有就空字符串\"\n" +
                    "}\n\n" +
                    "意图规则：\n" +
                    "1. 查询设备详情（数字示波器详情 / 介绍 / 参数 / 怎么样） → DETAIL\n" +
                    "2. 查询空闲设备（有哪些设备 / 可用设备 / 空闲设备） → LIST\n" +
                    "3. 查询预约记录（我的预约 / 我的记录 / 我预约了什么） → RECORD\n" +
                    "4. 其他所有问题 → CHAT\n\n" +
                    "注意：\n" +
                    "- 只能输出JSON\n" +
                    "- 不能输出多余文字\n" +
                    "- deviceName 只在 DETAIL 时填写"
            );
            messages.add(system);

            Map<String, String> user = new HashMap<>();
            user.put("role", "user");
            user.put("content", msg);
            messages.add(user);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "deepseek-v4-pro");
            body.put("messages", messages);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            return root.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText()
                    .trim();
        } catch (Exception e) {
            return "{\"intent\":\"CHAT\",\"deviceName\":\"\"}";
        }
    }
}