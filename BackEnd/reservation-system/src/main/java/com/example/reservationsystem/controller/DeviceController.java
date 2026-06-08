package com.example.reservationsystem.controller;

import com.example.reservationsystem.entity.Device;
import com.example.reservationsystem.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/list")
    public List<Device> list(){
        return deviceService.getAllDevices();
    }


    @GetMapping("/detail")
    public Device getDeviceDetail(String id){
        return deviceService.findById(id);
    }


    // 按管理员前缀查设备

    @GetMapping("/xxlist")

    public List<Device> list(String adminId){

        return deviceService.findByAdmin(adminId);

    }

    // 入库

    @PostMapping("/addNumber")

    public void addNumber(@RequestBody Map<String,Object> map){

        deviceService.xxaddNumber(

                map.get("id").toString(),

                Integer.parseInt(map.get("number").toString())

        );

    }

    // 出库

    @PostMapping("/reduceNumber")

    public boolean reduce(@RequestBody Map<String,Object> map){

        return deviceService.xxreduceNumber(

                map.get("id").toString(),

                Integer.parseInt(map.get("number").toString())

        );

    }

    // 新增设备
    @PostMapping("/add")

    public boolean add(@RequestBody Device device){

        return deviceService.addDevice(device);

    }

}