package com.TimeAttendance.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TimeAttendance.Models.Employee;
import com.TimeAttendance.Models.Overtime;
import com.TimeAttendance.Repositories.EmployeeRepository;
import com.TimeAttendance.Repositories.OvertimRepository;
import com.TimeAttendance.Service.OvertimeService;

@Service
public class OverTimeServiceImp implements OvertimeService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OvertimRepository overtimRepository;
    @Override
    public List<Overtime> getOvertimeById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
        return overtimRepository.findByEmployee(emp);
    }

}
