package com.exo.orderservice.order;

import com.exo.orderservice.customer.Customer;
import com.exo.orderservice.customer.CustomerSummaryDto;
import com.exo.orderservice.orderline.CreateOrderLineRequest;
import com.exo.orderservice.orderline.OrderLine;
import com.exo.orderservice.orderline.OrderLineDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "shippedDate", ignore = true)
    @Mapping(target = "deliveredDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "lines", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(CreateOrderRequest request);

    @Mapping(target = "productDescription", ignore = true)
    @Mapping(target = "lineTotal", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    OrderLine toEntity(CreateOrderLineRequest request);

    OrderResponse toResponse(Order order);

    @Mapping(target = "productId", ignore = true)
    OrderLineDto toDto(OrderLine line);

    CustomerSummaryDto toSummary(Customer customer);
}
