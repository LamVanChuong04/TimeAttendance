package com.TimeAttendance.Model;

import java.time.LocalDate;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
    private String phone;
    private String cccd;
    private String image;
    private String address;
    @OneToOne(mappedBy = "employee")
    private User user;
    public Employee(Long id, String fullName, String gender, LocalDate dateOfBirth, String phone, String cccd,
            String image, String address, User user, Department department, Division division, Job job,
            Position position) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.cccd = cccd;
        this.image = image;
        this.address = address;
        this.user = user;
        this.department = department;
        this.division = division;
        this.job = job;
        this.position = position;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @ManyToOne
    private Department department;

    @ManyToOne
    private Division division;

    public Employee(Long id, String fullName, String gender, LocalDate dateOfBirth, String phone, String cccd,
            String image, String address, Department department, Division division, Job job, Position position) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.cccd = cccd;
        this.image = image;
        this.address = address;
        this.department = department;
        this.division = division;
        this.job = job;
        this.position = position;
    }
    public Employee() {
    }
    @ManyToOne
    private Job job;

    @ManyToOne
    private Position position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    
}
