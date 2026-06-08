//package com.example.reservationsystem.service;
//import com.example.reservationsystem.entity.Reservation;
//import com.example.reservationsystem.mapper.ReservationMapper;
//import com.example.reservationsystem.vo.ReservationVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//
//@Service
//public class ReservationService {
//
//    @Autowired
//    private ReservationMapper reservationMapper;
//
//    public List<ReservationVO> findCurrent(String applyId){
//
//        return reservationMapper.findCurrent(applyId);
//    }
//
//
//
//    public void cancel(Integer id){
//
//        reservationMapper.cancel(id);
//
//    }
//
//    public void finish(Integer id){
//
//        reservationMapper.finish(id);
//
//    }
//
//
//    public List<ReservationVO> findAdminCurrent(){
//
//        return reservationMapper.findAdminCurrent();
//    }
//
//    public void approve(Integer id){
//        reservationMapper.approve(id);
//    }
//
////    public void reject(Integer id){
////        reservationMapper.reject(id);
////    }
//public void reject(Integer id,String rejectReason){
//    reservationMapper.reject(id,rejectReason);
//}
//
//
//}



package com.example.reservationsystem.service;

import com.example.reservationsystem.entity.Reservation;
import com.example.reservationsystem.mapper.DeviceMapper;
import com.example.reservationsystem.mapper.ReservationMapper;
import com.example.reservationsystem.vo.ReservationVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private DeviceMapper deviceMapper;


    // 普通用户当前预约
//    public List<ReservationVO> findCurrent(String applyId){
//        return reservationMapper.findCurrent(applyId);
//    }
    public List<Map<String, Object>> findCurrent(String applyId) {
        return reservationMapper.findCurrent(applyId);
    }


    // 管理员当前预约
    //临时测试
    public List<Map<String, Object>> findAdminCurrent(String applyId) {
        return reservationMapper.findAdminCurrent(applyId);
    }
//    public List<ReservationVO> findAdminCurrent(){
//
//        return reservationMapper.findAdminCurrent();
//    }


    // 取消预约
    public void cancel(Integer id){

        reservationMapper.cancel(id);
    }


    // 归还设备
    public void finish(Integer id){

        // 1. 查询预约对应设备
        String deviceId = reservationMapper.getDeviceId(id);

        // 2. 归还库存 +1
        deviceMapper.addNumber(deviceId);

        // 3. 修改预约状态
        reservationMapper.finish(id);
    }


    // 同意预约
    public boolean approve(Integer id){

        // 1. 查询预约对应设备
        String deviceId = reservationMapper.getDeviceId(id);

        // 2. 查询库存
        Integer number = deviceMapper.getNumber(deviceId);

        // 3. 判断库存
        if(number <= 0){

            return false;
        }

        // 4. 库存 -1
        deviceMapper.reduceNumber(deviceId);

        // 5. 修改预约状态
        reservationMapper.approve(id);

        return true;
    }


    // 拒绝预约
    public void reject(Integer id,String rejectReason){

        reservationMapper.reject(id,rejectReason);
    }

    // 普通用户历史预约
//    public List<ReservationVO> findUserHistory(String applyId){
//        return reservationMapper.findUserHistory(applyId);
//    }
    public List<Map<String, Object>> findUserHistory(String applyId) {
        return reservationMapper.findUserHistory(applyId);
    }


    // 管理员历史预约
    public List<Map<String, Object>> findAdminHistory(String applyId) {
        return reservationMapper.findAdminHistory(applyId);
    }
//    public List<ReservationVO> findAdminHistory(){
//        return reservationMapper.findAdminHistory();
//    }

    //添加预约信息
    public boolean add(Reservation r) {
        r.setStatus(0);
        return reservationMapper.insert(r) > 0;
    }



}