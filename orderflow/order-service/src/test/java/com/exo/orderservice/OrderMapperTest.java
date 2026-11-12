package com.exo.orderservice;

import com.exo.orderservice.common.Address;
import com.exo.orderservice.order.CreateOrderRequest;
import com.exo.orderservice.order.Order;
import com.exo.orderservice.order.OrderMapper;
import com.exo.orderservice.order.OrderMapperImpl;
import com.exo.orderservice.orderline.CreateOrderLineRequest;
import com.exo.orderservice.orderline.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {

    private final OrderMapper mapper = new OrderMapperImpl();

    @Test
    void mapsRequestFieldsOntoOrderAndLeavesServerManagedFieldsUntouched() {
        Address address = new Address();
        address.setRecipientName("Jane Doe");
        address.setStreetLine("1 Main St");
        address.setCity("Berlin");
        address.setPostalCode("10115");
        address.setCountry("DE");

        CreateOrderRequest request = new CreateOrderRequest(
                42L,
                address,
                "CARD",
                "EUR",
                List.of(new CreateOrderLineRequest(7L, "Widget", new BigDecimal("9.99"), 3)));

        Order order = mapper.toEntity(request);

        assertThat(order.getCurrency()).isEqualTo("EUR");
        assertThat(order.getPaymentMethod()).isEqualTo("CARD");
        assertThat(order.getAddress()).isEqualTo(address);

        assertThat(order.getId()).isNull();
        assertThat(order.getOrderNumber()).isNull();
        assertThat(order.getStatus()).isNull();
        assertThat(order.getCustomer()).isNull();
        assertThat(order.getLines()).isEmpty();
    }

    @Test
    void mapsOrderLineFieldsAndLeavesLineTotalForTheServiceToCompute() {
        CreateOrderLineRequest request = new CreateOrderLineRequest(7L, "Widget", new BigDecimal("9.99"), 3);

        OrderLine line = mapper.toEntity(request);

        assertThat(line.getProductName()).isEqualTo("Widget");
        assertThat(line.getUnitPrice()).isEqualTo(new BigDecimal("9.99"));
        assertThat(line.getQuantity()).isEqualTo(3);
        assertThat(line.getLineTotal()).isNull();
        assertThat(line.getOrder()).isNull();
    }
}
