package org.example.model.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CustomerRequest {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String email;
    private String phone;
    private LocalDate birthDate;
    private Double discount = 0.0;
}