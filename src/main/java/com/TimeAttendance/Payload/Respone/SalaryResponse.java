package com.TimeAttendance.Payload.Respone;

import java.math.BigDecimal;

public class SalaryResponse {
    private int year;
    private int month;

    private int workingDays;         // Số ngày làm việc trong tháng
    private int overtimeHours;       // Tổng số giờ tăng ca
    private BigDecimal baseSalary;       // Lương cơ bản (theo hợp đồng)
    private BigDecimal totalAllowance;   // Tổng phụ cấp trong tháng
    private BigDecimal overtimePay;      // Lương tăng ca
    private BigDecimal totalSalary;      // Tổng lương thực nhận

    public SalaryResponse() {}

    public SalaryResponse(int year, int month, int workingDays, int overtimeHours, BigDecimal baseSalary,
                          BigDecimal totalAllowance, BigDecimal overtimePay, BigDecimal totalSalary) {
        this.year = year;
        this.month = month;
        this.workingDays = workingDays;
        this.overtimeHours = overtimeHours;
        this.baseSalary = baseSalary;
        this.totalAllowance = totalAllowance;
        this.overtimePay = overtimePay;
        this.totalSalary = totalSalary;
    }

    // Getters và Setters

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

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getTotalAllowance() {
        return totalAllowance;
    }

    public void setTotalAllowance(BigDecimal totalAllowance) {
        this.totalAllowance = totalAllowance;
    }

    public BigDecimal getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(BigDecimal overtimePay) {
        this.overtimePay = overtimePay;
    }

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }
}

