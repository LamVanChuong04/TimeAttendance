package com.TimeAttendance.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ShiftType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double coefficient;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getCoefficient() {
        return coefficient;
    }
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
    @OneToMany(mappedBy = "shiftType")
    private List<Overtime> overtimes = new ArrayList<>();
    public List<Overtime> getOvertimes() {
        return overtimes;
    }
    public void setOvertimes(List<Overtime> overtimes) {
        this.overtimes = overtimes;
    }
    public ShiftType(Long id, String name, double coefficient, List<Overtime> overtimes) {
        this.id = id;
        this.name = name;
        this.coefficient = coefficient;
        this.overtimes = overtimes;
    }
    public ShiftType() {
    }
}
