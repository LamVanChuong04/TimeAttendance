package com.TimeAttendance.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Getter
@Setter

public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime checkInTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime checkOutTime;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public Attendance() {}

    public Attendance(LocalDate checkDate, LocalTime checkInTime, Employee employee) {
        this.checkDate = checkDate;
        this.checkInTime = checkInTime;
        this.employee = employee;
    }

    

    public Attendance(Long id, LocalDate checkDate, LocalTime checkInTime, LocalTime checkOutTime, Employee employee) {
        this.id = id;
        this.checkDate = checkDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.employee = employee;
    }

    
    

}
