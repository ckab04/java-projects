package com.exo.orderservice.order;


import com.exo.orderservice.common.Address;
import com.exo.orderservice.orderline.CreateOrderLineRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderRequest(
                                @NotNull(message = "Customer ID is required")
                                 Long customerId,

                                 @NotNull @Valid
                                 Address address,

                                 @NotBlank @Size(max = 50)
                                 String paymentMethod,

                                 @NotBlank @Size(min = 3, max = 3)
                                 String currency,

                                 @NotEmpty(message = "Order must contain at least one line")
                                 @Valid
                                 List<CreateOrderLineRequest> lines) {
}
