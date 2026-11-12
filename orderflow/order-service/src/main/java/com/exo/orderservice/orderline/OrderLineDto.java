package com.exo.orderservice.orderline;

import java.math.BigDecimal;

public record OrderLineDto(
        Long id,
        Long productId,       // if OrderLine references a Product
        String productName,   // snapshot, if you store it
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
