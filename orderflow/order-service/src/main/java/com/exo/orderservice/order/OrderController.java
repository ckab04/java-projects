package com.exo.orderservice.order;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }


    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse addOrder(@RequestBody CreateOrderRequest orderRequest){
        return this.orderService.addOrder(orderRequest);
    }


    @GetMapping("/orders/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId){
        return this.orderService.getOrder(orderId);
    }

}
