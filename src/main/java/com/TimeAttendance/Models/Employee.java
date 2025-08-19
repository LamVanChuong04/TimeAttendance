package com.TimeAttendance.Models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
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

    
    
}
