package com.TimeAttendance.Service;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.TimeAttendance.Models.Employee;


public interface EmployeeService {
    
    public Optional<Employee> getEmployeeById(Long id);
    public Boolean checkUsername(String username);
    // get all employee
    public Page<Employee> findByAllEmployee(Pageable pageable);
    // get search limit offset
    public Page<Employee> findByUserName(String username, Pageable pageable);
    public Optional<Employee> getFullName(String name);
    public Employee Save(Employee emp);
    public List<Employee> getAllEmployees();

    public void deleteEmployee(Long id);
}
