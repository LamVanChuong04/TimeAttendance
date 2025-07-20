package com.TimeAttendance.Payload.Respone;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceResponse {
    
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    public AttendanceResponse(LocalDate date, LocalTime checkInTime, LocalTime checkOutTime) {
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
    
}
