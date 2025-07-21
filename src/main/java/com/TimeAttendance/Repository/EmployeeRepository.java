package com.TimeAttendance.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // lấy danh sách nhân viên
    List<Employee> findAll();
    // tìm nhân viên theo id
    Optional<Employee> findById(Long id);
    // tìm nhân viên theo tên
    Optional<Employee> findByFullName(String fullName);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<Employee> findByUsername(String username);

    
} 
