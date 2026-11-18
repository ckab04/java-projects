package com.exo.orderservice;

import com.exo.orderservice.common.AddressDto;
import com.exo.orderservice.order.CreateOrderRequest;
import com.exo.orderservice.orderline.CreateOrderLineRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidCurrencyTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @ValueSource(strings = {"EUR", "usd", "CHF"})
    void acceptsIsoCurrencyCodes(String currency) {
        assertThat(validator.validate(requestWith(currency))).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"XYZ", "EURO", "EU", "123"})
    void rejectsUnknownCurrencyCodes(String currency) {
        assertThat(validator.validate(requestWith(currency)))
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactly("currency");
    }

    private CreateOrderRequest requestWith(String currency) {
        return new CreateOrderRequest(
                1L,
                new AddressDto("Jane Doe", "1 Main St", "Berlin", "10115", "DE"),
                "CARD",
                currency,
                List.of(new CreateOrderLineRequest(7L, "Widget", new BigDecimal("9.99"), 3)));
    }
}
