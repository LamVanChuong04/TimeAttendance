package com.TimeAttendance.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Models.Division;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
    Optional<Division> findById(Long id);

    
} 
