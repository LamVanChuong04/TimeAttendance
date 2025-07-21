package com.TimeAttendance.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TimeAttendance.Model.Employee;
import com.TimeAttendance.Model.Overtime;
import com.TimeAttendance.Repository.EmployeeRepository;
import com.TimeAttendance.Repository.OvertimRepository;

@Service
public class OvertimeService {
    private final OvertimRepository overtimeRepository;
    private final EmployeeRepository employeeRepository;
    public OvertimeService(OvertimRepository overtimeRepository, EmployeeRepository employeeRepository) {
        this.overtimeRepository = overtimeRepository;
        this.employeeRepository = employeeRepository;
    }
    public List<Overtime> getOvertimeByUsername(String username){
        Optional<Employee> employeeOpt = employeeRepository.findByFullName(username);
        if (employeeOpt.isEmpty()) {
            throw new RuntimeException("Employee not found for user: " + username);
        }
        return overtimeRepository.findByEmployee(employeeOpt.get());
        
    }
}
