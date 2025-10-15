package org.example.service;

import org.example.model.Schedule;
import org.example.model.dto.ScheduleRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    Schedule createSchedule(ScheduleRequest request);
    List<Schedule> getAllSchedules();
    Schedule updateSchedule(Long scheduleId, ScheduleRequest request);
    Optional<Schedule> getScheduleByDate(LocalDate date);
    Schedule updateTuesdaySchedule(ScheduleRequest request);
}
