package com.TimeAttendance.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Model.Overtime;
import com.TimeAttendance.Repository.OvertimRepository;
import com.TimeAttendance.Service.OvertimeService;

import java.net.Authenticator;
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
        String username = authentication.getName();
        List<Overtime> overtimeList = overtimeService.getOvertimeByUsername(username);
        if (overtimeList.isEmpty()) {
            return ResponseEntity.ok("Không có lịch sử tăng ca.");
        }
        return ResponseEntity.ok(overtimeList);
    }
    
}
    
