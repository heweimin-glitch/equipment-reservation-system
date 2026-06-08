package com.example.reservationsystem.vo;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationVO {
    private String id;
    private String apply_id;
    private String admin_id;
    private String applyName;
    private String adminName;
    private String device_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String purpose;
    private int status;
    private String  reject_reason;
    private String deviceName;
    private String address;
}