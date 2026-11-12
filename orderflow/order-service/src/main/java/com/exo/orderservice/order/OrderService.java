package com.exo.orderservice.order;


import com.exo.orderservice.common.OrderStatus;
import com.exo.orderservice.customer.Customer;
import com.exo.orderservice.customer.CustomerRepository;
import com.exo.orderservice.exceptions.CustomerNotFoundException;
import com.exo.orderservice.exceptions.OrderNotFound;
import com.exo.orderservice.orderline.CreateOrderLineRequest;
import com.exo.orderservice.orderline.OrderLine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;


    OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderMapper orderMapper){
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponse addOrder(CreateOrderRequest request){
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.customerId()));

        Order order = orderMapper.toEntity(request);
        order.setCustomer(customer);
        order.setOrderNumber(generateOrderNumber());
        order.setOrderDate(Instant.now());
        order.setStatus(OrderStatus.PENDING);

        BigDecimal subtotal = BigDecimal.ZERO;
        for (CreateOrderLineRequest lineRequest : request.lines()) {
            OrderLine line = orderMapper.toEntity(lineRequest);
            BigDecimal lineTotal = line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQuantity()));
            line.setLineTotal(lineTotal);
            order.addLine(line);
            subtotal = subtotal.add(lineTotal);
        }
        order.setSubtotal(subtotal);
        order.setTotalAmount(subtotal);

        Order saved = orderRepository.save(order);
        return orderMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFound(orderId));
        return orderMapper.toResponse(order);
    }

    private String generateOrderNumber(){
        return "ORD-" + UUID.randomUUID();
    }
}
