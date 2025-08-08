package com.TimeAttendance.Repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TimeAttendance.Models.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findById(Long id);

    
} 
