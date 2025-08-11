package com.TimeAttendance.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TimeAttendance.Models.Department;
import com.TimeAttendance.Repositories.DepartmentRepository;
import com.TimeAttendance.Service.DeparmentService;

@Service
public class DepartmentServiceImp implements DeparmentService{
    @Autowired
    private DepartmentRepository departmentRepository;

    public Optional<Department> getDepartment(Long id){
        return departmentRepository.findById(id);
    }
}
