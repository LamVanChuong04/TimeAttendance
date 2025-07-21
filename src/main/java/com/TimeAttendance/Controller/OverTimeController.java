package com.TimeAttendance.Controller;


import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Model.Overtime;
import com.TimeAttendance.Payload.Respone.OverTimeResponse;
import com.TimeAttendance.Service.OvertimeService;
import com.TimeAttendance.Service.UserDetailsImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;



@RestController

public class OverTimeController {
    private final OvertimeService overtimeService;
    public OverTimeController(OvertimeService overtimeService) {
        this.overtimeService = overtimeService;
    }
    // xem lịch sử tăng ca của nhân viên
    @GetMapping("/overtime")
    public ResponseEntity<?> getOvertime(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long id = userDetails.getId();
        // chứa danh sách lịch sử tăng ca
        List<Overtime> overtimeList = overtimeService.getOvertimeById(id);
        if (overtimeList.isEmpty()) {
            return ResponseEntity.ok("Không có lịch sử tăng ca.");
        }
        // list chứa các đối tượng OverTimeResponse
        // để trả về cho client
        List<OverTimeResponse> responseList = new ArrayList<>();

        for (Overtime overtime : overtimeList) {
            OverTimeResponse response = new OverTimeResponse(
                overtime.getYear(),
                overtime.getMonth(),
                overtime.getDay(),
                overtime.getHours()
                
            );
            responseList.add(response);
        }
        return ResponseEntity.ok(responseList);
    }


    
}
    
