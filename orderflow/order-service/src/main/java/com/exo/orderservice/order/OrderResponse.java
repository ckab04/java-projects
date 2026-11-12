package com.exo.orderservice.order;


import com.exo.orderservice.common.AddressDto;
import com.exo.orderservice.common.OrderStatus;
import com.exo.orderservice.customer.CustomerSummaryDto;
import com.exo.orderservice.orderline.OrderLineDto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        String orderNumber,
        Instant orderDate,
        Instant shippedDate,
        Instant deliveredDate,
        OrderStatus status,
        BigDecimal subtotal,
        BigDecimal totalAmount,
        String currency,
        String paymentMethod,
        AddressDto address,
        CustomerSummaryDto customer,
        List<OrderLineDto> lines
) {
}
