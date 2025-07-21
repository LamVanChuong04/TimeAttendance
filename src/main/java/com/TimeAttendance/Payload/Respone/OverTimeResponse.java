package com.TimeAttendance.Payload.Respone;

public class OverTimeResponse {
    private int year;
    private int month;
    private int day;
    private int hours;

    public OverTimeResponse(int year, int month, int day, int hours) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours = hours;
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
    
}
