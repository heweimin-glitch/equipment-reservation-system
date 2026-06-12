package com.example.reservationsystem.service;
import com.example.reservationsystem.entity.Device;
import com.example.reservationsystem.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 设备业务逻辑层
 * 处理设备查询、出入库、新增设备等
 */
@Service
public class DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    /** 获取全部设备列表 */
    public List<Device> getAllDevices() {
        return deviceMapper.findAll();
    }

    /** 根据ID查询设备详情 */
    public Device findById(String id) {
        return deviceMapper.findById(id);
    }

    /**
     * 根据管理员ID查询其管理的设备（按ID前2位前缀匹配学科）
     * @param adminId 管理员ID
     * @return 同类别设备列表
     */
    public List<Device> findByAdmin(String adminId) {
        return deviceMapper.findByPrefix(adminId.substring(0, 2));
    }

    /**
     * 设备入库：增加库存数量
     * @param id 设备ID
     * @param num 增加数量
     */
    public void xxaddNumber(String id, int num) {
        deviceMapper.xxaddNumber(id, num);
    }

    /**
     * 设备出库：减少库存数量
     * @param id 设备ID
     * @param num 减少数量
     * @return 成功返回 true，库存不足返回 false
     */
    public boolean xxreduceNumber(String id, int num) {
        return deviceMapper.xxreduceNumber(id, num) > 0;
    }

    /**
     * 新增设备
     * @param device 设备对象
     * @return 成功返回 true，ID重复返回 false
     */
    public boolean addDevice(Device device) {
        if (deviceMapper.exists(device.getId()) > 0) {
            return false;
        }
        deviceMapper.insert(device);
        return true;
    }
}