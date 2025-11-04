package org.example.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class EmployeeRequest {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String position;

    private String email;
    private String phone;

    @NotNull
    private LocalDate hireDate;

    private Boolean isActive = true;
}
