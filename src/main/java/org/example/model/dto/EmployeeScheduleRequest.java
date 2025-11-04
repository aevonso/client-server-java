package org.example.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EmployeeScheduleRequest {
    @NotNull
    private Long employeeId;

    @NotNull
    private LocalDate workDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private LocalTime breakStart;
    private LocalTime breakEnd;
    private String notes;
}
