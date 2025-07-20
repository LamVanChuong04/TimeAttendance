package com.TimeAttendance.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Model.Attendance;
import com.TimeAttendance.Model.Employee;

@Repository
public interface AttendanceRepository  extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeAndCheckDate(Employee employee, LocalDate checkDate);
    List<Attendance> findByEmployee(Employee employee);
}
