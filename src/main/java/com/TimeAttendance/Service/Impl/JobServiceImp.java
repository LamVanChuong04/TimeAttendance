package com.TimeAttendance.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TimeAttendance.Models.Job;
import com.TimeAttendance.Repositories.JobRepository;
import com.TimeAttendance.Service.JobService;

@Service
public class JobServiceImp implements JobService {
    @Autowired
    private JobRepository jobRepository;
    @Override
    public Optional<Job> getJob(Long id){
        return jobRepository.findById(id);
    }
}
