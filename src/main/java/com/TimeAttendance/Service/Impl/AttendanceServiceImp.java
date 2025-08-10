package com.TimeAttendance.Service.Impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TimeAttendance.Controllers.AttendanceController;
import com.TimeAttendance.Models.Attendance;
import com.TimeAttendance.Models.Employee;
import com.TimeAttendance.Payload.Respone.MessageResponse;
import com.TimeAttendance.Repositories.AttendanceRepository;
import com.TimeAttendance.Repositories.EmployeeRepository;
import com.TimeAttendance.Service.AttendanceService;

@Service
public class AttendanceServiceImp implements AttendanceService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> getAttendancesById(Long id) {
        return attendanceRepository.findByEmployeeId(id);
    }
    private Employee getEmployeeOrThrow(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
    }

    @Override
    public MessageResponse checkInAttendance(Long employeeId, LocalDate date, LocalTime time) {
        Employee employee = getEmployeeOrThrow(employeeId);

        if (attendanceRepository.existsByEmployeeAndCheckDate(employee, date)) {
            return new MessageResponse("Bạn đã chấm công hôm nay rồi!");
        }

        Attendance attendance = new Attendance(date, time, employee);
        attendanceRepository.save(attendance);

        return new MessageResponse("Chấm công vào thành công lúc " + time);
    }

    @Override
    public MessageResponse checkOutAttendance(Long employeeId, LocalDate date, LocalTime time) {
        Employee employee = getEmployeeOrThrow(employeeId);

        Attendance attendance = attendanceRepository.findByEmployeeAndCheckDate(employee, date)
                .orElse(null);

        if (attendance == null) {
            return new MessageResponse("Bạn chưa chấm công vào hôm nay.");
        }

        if (attendance.getCheckOutTime() != null) {
            return new MessageResponse("Bạn đã chấm công ra rồi.");
        }

        attendance.setCheckOutTime(time);
        attendanceRepository.save(attendance);

        return new MessageResponse("Chấm công ra thành công lúc " + time);
    }

}
