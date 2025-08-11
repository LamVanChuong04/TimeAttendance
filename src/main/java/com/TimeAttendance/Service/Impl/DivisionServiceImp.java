package com.TimeAttendance.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TimeAttendance.Models.Division;
import com.TimeAttendance.Repositories.DivisionRepository;
import com.TimeAttendance.Service.DivisionService;

@Service
public class DivisionServiceImp implements DivisionService{
    @Autowired
    private DivisionRepository divisionRepository;

    public Optional<Division> getDivision(Long id){
        return divisionRepository.findById(id);
    }
}
