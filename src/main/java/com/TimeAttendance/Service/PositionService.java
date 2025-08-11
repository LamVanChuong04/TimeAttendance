package com.TimeAttendance.Service;

import java.util.Optional;

import com.TimeAttendance.Models.Position;

public interface PositionService {
    public Optional<Position> getPosition(Long id);
}
