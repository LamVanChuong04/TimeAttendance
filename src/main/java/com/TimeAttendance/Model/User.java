package com.TimeAttendance.Model;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    private String username;
    private String password;
    private String email;
    private String fullName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name = "user_role", 
    joinColumns = @JoinColumn(name = "UserID"), 
    inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Role> roles = new HashSet<>();
    private boolean active = true;

    public User() {
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public User(Long userID, String username, String password, String email, String fullName, Set<Role> roles,
            boolean active, Employee employee) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
        this.active = active;
        this.employee = employee;
    }
    
}
