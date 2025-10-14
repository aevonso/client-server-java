package org.example.model.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class CoffeeTypeRequest {
    @NotNull
    private String name;

    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    private Boolean isAvailable = true;
}
