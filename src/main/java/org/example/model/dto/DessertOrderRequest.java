package org.example.model.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class DessertOrderRequest {
    @NotNull
    private Long dessertId;

    @NotNull
    @Positive
    private Integer quantity;

    private String customerName;
    private String waiterName;
    private String customerPhone;
}
