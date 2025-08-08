package com.TimeAttendance.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Models.Employee;
import com.TimeAttendance.Models.Overtime;

import java.util.List;


@Repository
public interface OvertimRepository extends JpaRepository<Overtime, Long> {
    List<Overtime> findByEmployee(Employee employee);

    
} 