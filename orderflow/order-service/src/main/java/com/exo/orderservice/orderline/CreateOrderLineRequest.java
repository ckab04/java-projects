package com.exo.orderservice.orderline;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateOrderLineRequest(
        @NotNull
        Long productId,
        @NotBlank
        String productName,
        @NotNull @DecimalMin(value = "0.01")
        BigDecimal unitPrice,
        @NotNull @Min(1)
        Integer quantity)
{}
