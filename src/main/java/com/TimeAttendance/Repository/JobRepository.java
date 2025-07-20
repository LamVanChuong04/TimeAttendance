package com.TimeAttendance.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findById(Long id);

    
} 
