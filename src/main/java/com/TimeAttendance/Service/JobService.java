package com.TimeAttendance.Service;

import java.util.Optional;

import com.TimeAttendance.Models.Job;

public interface JobService {
    public Optional<Job> getJob(Long id);
}
