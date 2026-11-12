package com.exo.orderservice.order;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }


    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse addOrder(@Valid @RequestBody CreateOrderRequest orderRequest){
        return this.orderService.addOrder(orderRequest);
    }


    @GetMapping("/orders/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId){
        return this.orderService.getOrder(orderId);
    }

}
