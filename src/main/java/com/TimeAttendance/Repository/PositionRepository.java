package com.TimeAttendance.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findById(Long id);

    
} 
