package com.TimeAttendance.Service;

import java.util.Optional;

import com.TimeAttendance.Models.Division;

public interface DivisionService {
    public Optional<Division> getDivision(Long id);
}
