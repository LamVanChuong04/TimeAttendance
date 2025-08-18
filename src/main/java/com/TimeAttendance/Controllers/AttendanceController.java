package com.TimeAttendance.Controllers;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Models.Attendance;

import com.TimeAttendance.Payload.Respone.AttendanceResponse;
import com.TimeAttendance.Payload.Respone.MessageResponse;

import com.TimeAttendance.Service.Impl.AttendanceServiceImp;

import com.TimeAttendance.Service.Impl.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@Tag(name = "Attendance Controller")
public class AttendanceController {
    @Autowired
    private AttendanceServiceImp attendanceServiceImp;
    

    @Operation(summary = "Check-in", description = "Điểm danh")
    @PostMapping("/attendance/check-in")
    public ResponseEntity<?> checkInAttendance() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

    return ResponseEntity.ok(
        attendanceServiceImp.checkInAttendance(userDetails.getId(), LocalDate.now(), LocalTime.now())
    );
}
    @Operation(summary = "Check-out", description = "Điểm danh về")
    @PostMapping("/attendance/check-out")
    public ResponseEntity<?> checkOutAttendance() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

    return ResponseEntity.ok(
        attendanceServiceImp.checkOutAttendance(userDetails.getId(), LocalDate.now(), LocalTime.now())
    );
}
    // xem bảng công của bản thân
    @Operation(summary = "Get attendace", description = "Lấy bảng lương nhân viên")
    @GetMapping("/attendance")
    public ResponseEntity<?> getAttendanceOfEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Attendance> att = attendanceServiceImp.getAttendancesById(userDetails.getId());
        List<AttendanceResponse> responseList = new ArrayList<>();
        for (Attendance a : att) {
            responseList.add(new AttendanceResponse(
                a.getCheckDate(),
                a.getCheckInTime(),
                a.getCheckOutTime()
            ));
        }
        if (responseList.isEmpty()) {
            return ResponseEntity.ok().body(new MessageResponse("Bạn chưa có bảng công nào."));
        }
        return ResponseEntity.ok(responseList);
    }
    // xem bảng công của nhân viên
    @Operation(summary = "Get attendance of id", description = "Lấy bảng lương nhân viên theo id")
    @GetMapping("/attendance/{id}")
    public ResponseEntity<?> getAttendanceById(@PathVariable Long id) {
        List<Attendance> att = attendanceServiceImp.getAttendancesById(id);
        List<AttendanceResponse> responseList = new ArrayList<>();
        for (Attendance a : att) {
            responseList.add(new AttendanceResponse(
                a.getCheckDate(),
                a.getCheckInTime(),
                a.getCheckOutTime()
            ));
        }
        return ResponseEntity.ok(responseList);
    }
    
}
