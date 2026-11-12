package com.exo.orderservice.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDto(
        @NotBlank @Size(max = 255)
        String recipientName,

        @NotBlank @Size(max = 255)
        String streetLine,

        @NotBlank @Size(max = 100)
        String city,

        @NotBlank @Size(max = 20)
        String postalCode,

        @NotBlank @Size(min = 2, max = 2)
        String country) {
}
