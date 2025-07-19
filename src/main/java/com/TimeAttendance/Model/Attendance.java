package com.TimeAttendance.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Attendance {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Attendance(Long id, int year, int month, int day, int hourIn, int minuteIn, int hourOut, int minuteOut,
            Employee employee) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hourIn = hourIn;
        this.minuteIn = minuteIn;
        this.hourOut = hourOut;
        this.minuteOut = minuteOut;
        this.employee = employee;
    }

    private int year;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHourIn() {
        return hourIn;
    }

    public void setHourIn(int hourIn) {
        this.hourIn = hourIn;
    }

    public int getMinuteIn() {
        return minuteIn;
    }

    public void setMinuteIn(int minuteIn) {
        this.minuteIn = minuteIn;
    }

    public int getHourOut() {
        return hourOut;
    }

    public void setHourOut(int hourOut) {
        this.hourOut = hourOut;
    }

    public int getMinuteOut() {
        return minuteOut;
    }

    public void setMinuteOut(int minuteOut) {
        this.minuteOut = minuteOut;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private int month;
    private int day;
    private int hourIn;
    private int minuteIn;
    private int hourOut;
    private int minuteOut;

    @ManyToOne
    private Employee employee;

}
