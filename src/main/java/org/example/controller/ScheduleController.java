package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Schedule;
import org.example.model.dto.ScheduleRequest;
import org.example.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody ScheduleRequest request) {
        Schedule schedule = scheduleService.createSchedule(request);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleRequest request) {
        Schedule schedule = scheduleService.updateSchedule(scheduleId, request);
        return ResponseEntity.ok(schedule);
    }

    @PutMapping("/tuesday")
    public ResponseEntity<Schedule> updateTuesdaySchedule(@Valid @RequestBody ScheduleRequest request) {
        Schedule schedule = scheduleService.updateTuesdaySchedule(request);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/date")
    public ResponseEntity<Schedule> getScheduleByDate(@RequestParam LocalDate date) {
        return scheduleService.getScheduleByDate(date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
