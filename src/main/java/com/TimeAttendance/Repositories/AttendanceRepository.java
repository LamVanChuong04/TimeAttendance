package com.TimeAttendance.Repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Models.Attendance;
import com.TimeAttendance.Models.Employee;

@Repository
public interface AttendanceRepository  extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeAndCheckDate(Employee employee, LocalDate checkDate);
    List<Attendance> findByEmployee(Employee employee);
}
