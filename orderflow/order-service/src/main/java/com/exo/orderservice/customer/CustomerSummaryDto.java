package com.exo.orderservice.customer;

public record CustomerSummaryDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
