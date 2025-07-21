package com.TimeAttendance.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Model.Employee;
import com.TimeAttendance.Model.Overtime;
import java.util.List;


@Repository
public interface OvertimRepository extends JpaRepository<Overtime, Long> {
    List<Overtime> findByEmployee(Employee employee);

    
} 