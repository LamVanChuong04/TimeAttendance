package com.TimeAttendance.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import com.TimeAttendance.Models.Attendance;

import com.TimeAttendance.Payload.Respone.MessageResponse;

public interface AttendanceService {
    public List<Attendance> getAttendancesById(Long id);
    MessageResponse checkInAttendance(Long employeeId, LocalDate date, LocalTime time);
    MessageResponse checkOutAttendance(Long employeeId, LocalDate date, LocalTime time);
}
