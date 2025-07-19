package com.TimeAttendance.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Insurance {
    public Insurance(Long id, String number, LocalDate issueDate, String issuePlace, String registeredHospital,
            Employee employee) {
        this.id = id;
        this.number = number;
        this.issueDate = issueDate;
        this.issuePlace = issuePlace;
        this.registeredHospital = registeredHospital;
        this.employee = employee;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public String getRegisteredHospital() {
        return registeredHospital;
    }

    public void setRegisteredHospital(String registeredHospital) {
        this.registeredHospital = registeredHospital;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private String number;
    private LocalDate issueDate;
    private String issuePlace;
    private String registeredHospital;

    @OneToOne
    private Employee employee;
}
