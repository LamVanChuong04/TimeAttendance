package com.TimeAttendance.Service;

import java.util.List;

import com.TimeAttendance.Models.Overtime;



public interface OvertimeService {
    public List<Overtime> getOvertimeById(Long id);
    
}
