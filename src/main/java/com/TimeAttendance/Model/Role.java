package com.TimeAttendance.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {
    }
    public Role(ERole name) {
        this.name = name;
    }
    public Integer getId() {
        return roleId;
    }
    public void setId(Integer id) {
        this.roleId = id;
    }
    public ERole getName() {
        return name;
    }
    public void setName(ERole name) {
        this.name = name;
    }
    public Role(Integer id, ERole name) {
        this.roleId = id;
        this.name = name;
    }
    
}
