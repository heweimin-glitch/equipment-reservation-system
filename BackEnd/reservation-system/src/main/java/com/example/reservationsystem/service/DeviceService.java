package com.example.reservationsystem.service;
import com.example.reservationsystem.entity.Device;
import com.example.reservationsystem.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    public List<Device> getAllDevices(){
        return deviceMapper.findAll();
    }


    public Device findById(String id){
        return deviceMapper.findById(id);
    }

    public List<Device> findByAdmin(String adminId){

        return deviceMapper.findByPrefix(adminId.substring(0,2));

    }

    public void xxaddNumber(String id, int num){

        deviceMapper.xxaddNumber(id,num);

    }


    public boolean xxreduceNumber(String id, int num){
        return deviceMapper.xxreduceNumber(id,num) > 0;
    }


    public boolean addDevice(Device device){
        if(deviceMapper.exists(device.getId()) > 0){
            return false;
        }

        deviceMapper.insert(device);
        return true;
    }
}