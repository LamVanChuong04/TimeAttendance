package com.TimeAttendance.Service;

import java.util.Optional;

import com.TimeAttendance.Models.Department;

public interface DeparmentService {
    public Optional<Department> getDepartment(Long id);
}
