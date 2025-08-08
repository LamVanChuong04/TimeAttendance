package com.TimeAttendance.Models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

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
    private String password;
    private String username;
    private String email;
    private boolean active = true;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name = "user_role", 
    joinColumns = @JoinColumn(name = "employee_id"), 
    inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Role> roles = new HashSet<>();

    public String getEmail() {
        return email;
    }
   


    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    

    

    public Employee(Long id, String fullName, String gender, LocalDate dateOfBirth, String phone, String cccd,
            String image, String address, String password, String username, String email, boolean active,
            Set<Role> roles, Department department, Division division, Job job, Position position,
            List<Overtime> overtimes) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.cccd = cccd;
        this.image = image;
        this.address = address;
        this.password = password;
        this.username = username;
        this.email = email;
        this.active = active;
        this.roles = roles;
        
        this.department = department;
        this.division = division;
        this.job = job;
        this.position = position;
        this.overtimes = overtimes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @ManyToOne
    private Department department;

    @ManyToOne
    private Division division;

    @ManyToOne
    private Job job;

    @ManyToOne
    private Position position;

    // Một nhân viên có thể có nhiều lần tăng ca
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Overtime> overtimes;

    
    public Employee() {
    }

    

    // Getters & Setters

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

    public List<Overtime> getOvertimes() {
        return overtimes;
    }

    public void setOvertimes(List<Overtime> overtimes) {
        this.overtimes = overtimes;
    }

    
}
