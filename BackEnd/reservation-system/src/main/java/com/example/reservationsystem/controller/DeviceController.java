package com.example.reservationsystem.controller;

import com.example.reservationsystem.entity.Device;
import com.example.reservationsystem.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 设备接口
 * 提供设备列表、详情、出入库、新增等 REST API
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /** GET /device/list —— 获取全部设备列表 */
    @GetMapping("/list")
    public List<Device> list() {
        return deviceService.getAllDevices();
    }

    /** GET /device/detail?id=xxx —— 查询设备详情 */
    @GetMapping("/detail")
    public Device getDeviceDetail(String id) {
        return deviceService.findById(id);
    }

    /**
     * GET /device/xxlist?adminId=xxx —— 按管理员学科前缀查询设备列表
     * 仅返回与管理员同类别（ID前2位相同）的设备
     */
    @GetMapping("/xxlist")
    public List<Device> list(String adminId) {
        return deviceService.findByAdmin(adminId);
    }

    /**
     * POST /device/addNumber —— 设备入库
     * @param map {id: 设备ID, number: 入库数量}
     */
    @PostMapping("/addNumber")
    public void addNumber(@RequestBody Map<String, Object> map) {
        deviceService.xxaddNumber(
                map.get("id").toString(),
                Integer.parseInt(map.get("number").toString())
        );
    }

    /**
     * POST /device/reduceNumber —— 设备出库
     * @param map {id: 设备ID, number: 出库数量}
     * @return 库存充足时返回 true，不足返回 false
     */
    @PostMapping("/reduceNumber")
    public boolean reduce(@RequestBody Map<String, Object> map) {
        return deviceService.xxreduceNumber(
                map.get("id").toString(),
                Integer.parseInt(map.get("number").toString())
        );
    }

    /** POST /device/add —— 新增设备 */
    @PostMapping("/add")
    public boolean add(@RequestBody Device device) {
        return deviceService.addDevice(device);
    }

}