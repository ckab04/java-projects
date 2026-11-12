package com.exo.orderservice.order;

import com.exo.orderservice.customer.Customer;
import com.exo.orderservice.customer.CustomerSummaryDto;
import com.exo.orderservice.orderline.CreateOrderLineRequest;
import com.exo.orderservice.orderline.OrderLine;
import com.exo.orderservice.orderline.OrderLineDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface OrderMapper {

    @BeanMapping(ignoreUnmappedSourceProperties = {"customerId", "lines"})
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

    @BeanMapping(ignoreUnmappedSourceProperties = {"createdAt", "updatedAt"})
    OrderResponse toResponse(Order order);

    @BeanMapping(ignoreUnmappedSourceProperties = {"createdAt", "updatedAt", "productDescription", "order"})
    OrderLineDto toDto(OrderLine line);

    @BeanMapping(ignoreUnmappedSourceProperties = {"createdAt", "updatedAt", "dateOfBirth", "address"})
    CustomerSummaryDto toSummary(Customer customer);
}
