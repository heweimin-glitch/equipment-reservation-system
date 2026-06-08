//package com.example.reservationsystem.controller;
//
//import com.example.reservationsystem.entity.Reservation;
//import com.example.reservationsystem.service.ReservationService;
//
//import com.example.reservationsystem.vo.ReservationVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/reservation")
//public class ReservationController {
//
//    @Autowired
//    private ReservationService reservationService;
//
////    @GetMapping("/current")
////    public List<ReservationVO> current(String applyId){
////
////        return reservationService.findCurrent(applyId);
////    }
//
//    @GetMapping("/current")
//    public List<ReservationVO> current(String applyId,Integer isAdmin){
//
//        if(isAdmin == 1){
//            return reservationService.findAdminCurrent();
//        }
//
//        return reservationService.findCurrent(applyId);
//    }
//
//
//    // 取消预约
//    @PostMapping("/cancel")
//    public void cancel(@RequestBody Map<String, Integer> map){
//
//        Integer id = map.get("id");
//
//        reservationService.cancel(id);
//
//    }
//
//    // 归还设备
//    @PostMapping("/finish")
//    public void finish(@RequestBody Map<String, Integer> map){
//
//        Integer id = map.get("id");
//
//        reservationService.finish(id);
//
//    }
//
//    // 同意预约
//    @PostMapping("/approve")
//    public void approve(@RequestBody Map<String,Integer> map){
//
//        Integer id = map.get("id");
//
//        reservationService.approve(id);
//    }
//
//    // 拒绝预约
//    // 拒绝预约
//    @PostMapping("/reject")
//    public void reject(@RequestBody Map<String,Object> map){
//
//        Integer id = Integer.parseInt(map.get("id").toString());
//
//        String rejectReason = map.get("rejectReason").toString();
//
//        reservationService.reject(id,rejectReason);
//    }
//
//
//}



package com.example.reservationsystem.controller;

import com.example.reservationsystem.entity.Reservation;
import com.example.reservationsystem.service.ReservationService;
import com.example.reservationsystem.vo.ReservationVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;


//     当前预约
    @GetMapping("/current")
    public List<Map<String, Object>> current(String applyId,Integer isAdmin) {
        if (isAdmin == 1){
            List<Map<String, Object>> list = reservationService.findAdminCurrent(applyId);
            return list;
        }
            List<Map<String, Object>> list = reservationService.findCurrent(applyId);
            return list;
}
//    @GetMapping("/current")
//    public List<ReservationVO> current(String applyId,Integer isAdmin){
//        if(isAdmin == 1){
//            return reservationService.findAdminCurrent();
//        }
//        return reservationService.findCurrent(applyId);
//    }

// 历史预约
    @GetMapping("/history")
    public List<Map<String,Object>> history(String applyId,Integer isAdmin){
    // 管理员
    if(isAdmin == 1){
        List<Map<String, Object>> list = reservationService.findAdminHistory(applyId);
        return list;
//        return reservationService.findAdminHistory(applyId);
    }

    // 普通用户
        List<Map<String, Object>> list = reservationService.findUserHistory(applyId);
        return list;
//    return reservationService.findUserHistory(applyId);
}

    // 取消预约
    @PostMapping("/cancel")
    public void cancel(@RequestBody Map<String, Integer> map){

        Integer id = map.get("id");

        reservationService.cancel(id);
    }


    // 归还设备
    @PostMapping("/finish")
    public void finish(@RequestBody Map<String, Integer> map){

        Integer id = map.get("id");

        reservationService.finish(id);
    }


    // 同意预约
    @PostMapping("/approve")
    public boolean approve(@RequestBody Map<String,Integer> map){

        Integer id = map.get("id");

        return reservationService.approve(id);
    }


    // 拒绝预约
    @PostMapping("/reject")
    public void reject(@RequestBody Map<String,Object> map){
        Integer id = Integer.parseInt(map.get("id").toString());
        String rejectReason = map.get("rejectReason").toString();
        reservationService.reject(id,rejectReason);
    }


    //新增预约
    @PostMapping("/add")
    public boolean add(@RequestBody Reservation r) {
        System.out.println("收到预约：" + r);
        return reservationService.add(r);
    }


}