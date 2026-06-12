package com.example.reservationsystem.dto;

/**
 * AI 意图解析结果 DTO
 * DeepSeek 返回的 JSON 解析后映射为此对象
 */
public class IntentDTO {
    /** 意图类型：DETAIL=设备详情 / LIST=设备列表 / RECORD=预约记录 / CHAT=普通聊天 */
    public String intent;
    /** 设备名称（仅 intent=DETAIL 时有值，其他情况为空） */
    public String deviceName;
}