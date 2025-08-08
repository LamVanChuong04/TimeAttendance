package com.TimeAttendance.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
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

    public Long getId() {
        return id;
    }

    public Attendance(Long id, LocalDate checkDate, LocalTime checkInTime, LocalTime checkOutTime, Employee employee) {
        this.id = id;
        this.checkDate = checkDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.employee = employee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDate checkDate) {
        this.checkDate = checkDate;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    

}
