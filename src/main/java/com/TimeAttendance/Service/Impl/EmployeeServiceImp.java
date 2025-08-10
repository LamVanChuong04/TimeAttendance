package com.TimeAttendance.Service.Impl;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.TimeAttendance.Models.Employee;
import com.TimeAttendance.Repositories.EmployeeRepository;
import com.TimeAttendance.Service.EmployeeService;

@Service
public class EmployeeServiceImp implements EmployeeService{
    
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public Optional<Employee> getEmployeeById(Long id){
        return employeeRepository.findById(id);
    }
    @Override
    public Boolean checkUsername(String userName){
        return employeeRepository.existsByUsername(userName);
    }

    @Override
    public Page<Employee> findByAllEmployee(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> findByUserName(String username, Pageable pageable) {
        return employeeRepository.findByUsername(username, pageable);
    }

}
