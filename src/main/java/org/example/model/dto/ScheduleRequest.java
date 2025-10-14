package org.example.model.dto;


import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleRequest {
    @NotNull
    private String dayOfWeek;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime openTime;

    @NotNull
    private LocalTime closeTime;

    private String specialNotes;
}
