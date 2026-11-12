package com.exo.orderservice.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select nextval('order_number_seq')", nativeQuery = true)
    long nextOrderNumberSequence();
}
