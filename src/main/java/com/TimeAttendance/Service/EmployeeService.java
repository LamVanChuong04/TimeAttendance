package com.TimeAttendance.Service;



import java.util.Optional;

import com.TimeAttendance.Models.Employee;

public interface EmployeeService {
    
    public Optional<Employee> getEmployeeById(Long id);
    
}
