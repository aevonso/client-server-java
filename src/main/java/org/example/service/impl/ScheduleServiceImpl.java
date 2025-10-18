package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.Schedule;
import org.example.model.dto.ScheduleRequest;
import org.example.repository.ScheduleRepository;
import org.example.service.ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public Schedule createSchedule(ScheduleRequest request) {
        // Проверяем, существует ли уже расписание на эту дату
        if (scheduleRepository.findByDate(request.getDate()).isPresent()) {
            throw new RuntimeException("Schedule for this date already exists");
        }

        Schedule schedule = new Schedule();
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setDate(request.getDate());
        schedule.setOpenTime(request.getOpenTime());
        schedule.setCloseTime(request.getCloseTime());
        schedule.setSpecialNotes(request.getSpecialNotes());
        schedule.setUpdatedAt(LocalDateTime.now());

        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional
    public Schedule updateSchedule(Long scheduleId, ScheduleRequest request) {
        Schedule existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        existingSchedule.setDayOfWeek(request.getDayOfWeek());
        existingSchedule.setOpenTime(request.getOpenTime());
        existingSchedule.setCloseTime(request.getCloseTime());
        existingSchedule.setSpecialNotes(request.getSpecialNotes());
        existingSchedule.setUpdatedAt(LocalDateTime.now());

        return scheduleRepository.save(existingSchedule);
    }

    @Override
    public Optional<Schedule> getScheduleByDate(LocalDate date) {
        return scheduleRepository.findByDate(date);
    }

    @Override
    @Transactional
    public Schedule updateTuesdaySchedule(ScheduleRequest request) {
        // Находим ближайший вторник
        LocalDate nextTuesday = findNextTuesday();

        Optional<Schedule> existingSchedule = scheduleRepository.findByDate(nextTuesday);

        Schedule schedule;
        if (existingSchedule.isPresent()) {
            schedule = existingSchedule.get();
        } else {
            schedule = new Schedule();
            schedule.setDate(nextTuesday);
        }

        schedule.setDayOfWeek("Вторник");
        schedule.setOpenTime(request.getOpenTime());
        schedule.setCloseTime(request.getCloseTime());
        schedule.setSpecialNotes(request.getSpecialNotes());
        schedule.setUpdatedAt(LocalDateTime.now());

        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        if(!scheduleRepository.existsById(scheduleId)) {
            throw new RuntimeException("Schedule not found");
        }
        scheduleRepository.deleteById(scheduleId);
    }

    @Override
    public void deleteScheduleByDate(LocalDate date) {
        Optional<Schedule> schedule = scheduleRepository.findByDate(date);
        if(schedule.isPresent()) {
            scheduleRepository.delete(schedule.get());
        }
        else {
            throw new RuntimeException("Schedule not found" + date + " not found");
        }
    }


    @Override
    public void deleteSchedulesBetweenDates(LocalDate start, LocalDate end) {
        List<Schedule> schedules = scheduleRepository.findByDateBetween(start, end);
        if (schedules.isEmpty()) {
            throw new RuntimeException("No schedules found between " + start + " and " + end);
        }
        scheduleRepository.deleteAll(schedules);
    }

    private LocalDate findNextTuesday() {
        LocalDate today = LocalDate.now();
        LocalDate nextTuesday = today;

        while (nextTuesday.getDayOfWeek() != DayOfWeek.TUESDAY) {
            nextTuesday = nextTuesday.plusDays(1);
        }

        return nextTuesday;
    }
}
