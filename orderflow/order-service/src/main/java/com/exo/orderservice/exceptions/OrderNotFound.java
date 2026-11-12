package com.exo.orderservice.exceptions;

public class OrderNotFound extends RuntimeException{

    public OrderNotFound(Long orderId){
        super("Order not found: " + orderId);
    }

}
