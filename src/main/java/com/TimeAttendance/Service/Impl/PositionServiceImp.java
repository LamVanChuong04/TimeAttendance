package com.TimeAttendance.Service.Impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.TimeAttendance.Models.Position;
import com.TimeAttendance.Repositories.PositionRepository;
import com.TimeAttendance.Service.PositionService;

@Service
public class PositionServiceImp implements PositionService{
    
    private PositionRepository positionRepository;

    @Override
    public Optional<Position> getPosition(Long id){
        return positionRepository.findById(id);
    }
}
