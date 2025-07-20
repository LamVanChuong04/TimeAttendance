package com.TimeAttendance.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Model.Division;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
    Optional<Division> findById(Long id);

    
} 
