package com.example.reservationsystem.entity;
import lombok.Data;

/**
 * 设备实体
 * 对应数据库 Equipments 表，存储实验设备的完整信息
 */
@Data
public class Device {
    /** 设备ID（前2位为学科分类编号） */
    private String id;
    /** 设备名称 */
    private String name;
    /** 设备简介/功能说明 */
    private String introduction;
    /** 设备存放地址/实验室名称 */
    private String address;
    /** 设备图片COS链接 */
    private String ImageUrl;
    /** 当前可借数量 */
    private int number;
    /** 设备规格参数 */
    private String spec;
}
