package com.TimeAttendance.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    // lấy danh sách nhân viên
    List<Employee> findAll();
    // tìm nhân viên theo id
    Optional<Employee> findById(Long id);
    // tìm nhân viên theo tên
    Optional<Employee> findByFullName(String fullName);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<Employee> findByUsername(String username);
    // use pagable
    Page<Employee> findByUsername(String username, Pageable pageable);


    
} 
