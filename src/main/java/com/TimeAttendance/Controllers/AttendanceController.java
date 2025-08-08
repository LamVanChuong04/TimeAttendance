package com.TimeAttendance.Controllers;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TimeAttendance.Models.Attendance;
import com.TimeAttendance.Models.Employee;
import com.TimeAttendance.Payload.Respone.AttendanceResponse;
import com.TimeAttendance.Payload.Respone.MessageResponse;
import com.TimeAttendance.Repositories.AttendanceRepository;
import com.TimeAttendance.Repositories.EmployeeRepository;
import com.TimeAttendance.Service.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@Tag(name = "Attendance Controller")
public class AttendanceController {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    public AttendanceController(EmployeeRepository employeeRepository, AttendanceRepository attendanceRepository) {
        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Operation(summary = "Check-in", description = "Điểm danh")
    @PostMapping("/attendance/check-in")
    public ResponseEntity<?> checkInAttendance() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    Optional<Employee> employeeOpt = employeeRepository.findById(userDetails.getId());
    if (employeeOpt.isEmpty()) {
        return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy nhân viên."));
    }

    Employee employee = employeeOpt.get();

    LocalDate today = LocalDate.now();
    LocalTime now = LocalTime.now();

    // Kiểm tra nếu đã chấm công hôm nay
    Optional<Attendance> existing = attendanceRepository.findByEmployeeAndCheckDate(employee, today);
    if (existing.isPresent()) {
        return ResponseEntity.badRequest().body(new MessageResponse("Bạn đã chấm công hôm nay rồi!"));
    }

    Attendance attendance = new Attendance(today, now, employee);
    attendanceRepository.save(attendance);

    return ResponseEntity.ok(new MessageResponse("Chấm công thành công lúc " + now));
    }
    @Operation(summary = "Check-out", description = "Điểm danh về")
    @PostMapping("/attendance/check-out")
    public ResponseEntity<?> checkOutAttendance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<Employee> employeeOpt = employeeRepository.findById(userDetails.getId());
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.ok().body(new MessageResponse("Không tìm thấy nhân viên."));
        }

        Employee employee = employeeOpt.get();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Optional<Attendance> existingOpt = attendanceRepository.findByEmployeeAndCheckDate(employee, today);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.ok().body(new MessageResponse("Bạn chưa chấm công vào hôm nay."));
        }

        Attendance attendance = existingOpt.get();

        if (attendance.getCheckOutTime() != null) {
            return ResponseEntity.ok().body(new MessageResponse("Bạn đã chấm công ra rồi."));
        }

        attendance.setCheckOutTime(now);
        attendanceRepository.save(attendance);

        return ResponseEntity.ok(new MessageResponse("Chấm công ra thành công lúc " + now));
    }
    // xem bảng công của bản thân
    @Operation(summary = "Get attendace", description = "Lấy bảng lương nhân viên")
    @GetMapping("/attendance")
    public ResponseEntity<?> getAttendanceOfEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<Employee> employee = employeeRepository.findById(userDetails.getId());
        List<Attendance> att = attendanceRepository.findByEmployee(employee.get());
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
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            return ResponseEntity.ok().body(new MessageResponse("Không tìm thấy nhân viên."));
        }
        List<Attendance> att = attendanceRepository.findByEmployee(employee.get());
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
