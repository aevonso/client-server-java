package org.example.model.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CoffeeOrderRequest {
    @NotNull
    private Long coffeeTypeId;

    @NotNull
    @Positive
    private Integer quantity;

    private String customerName;
    private String waiterName;
    private String customerPhone;
    private Long baristaId;
}
