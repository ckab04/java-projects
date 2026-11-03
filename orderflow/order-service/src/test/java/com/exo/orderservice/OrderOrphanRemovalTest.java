package com.exo.orderservice;

import com.exo.orderservice.common.OrderStatus;
import com.exo.orderservice.customer.Customer;
import com.exo.orderservice.order.Order;
import com.exo.orderservice.orderline.OrderLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostgresContainerConfig.class)
class OrderOrphanRemovalTest {

    @Autowired
    TestEntityManager em;

    @Test
    void removingLineFromCollectionDeletesItFromDatabase() {
        Customer customer = new Customer();
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        customer.setEmail("jane@example.com");

        Order order = new Order();
        order.setOrderNumber("ORD-TEST-001");
        order.setOrderDate(Instant.now());
        order.setStatus(OrderStatus.PENDING);
        order.setSubtotal(new BigDecimal("20.00"));
        order.setTotalAmount(new BigDecimal("20.00"));
        order.setCurrency("EUR");
        order.setCustomer(customer);

        order.addLine(buildLine("Widget A"));
        order.addLine(buildLine("Widget B"));

        em.persistAndFlush(customer);
        em.persistAndFlush(order);
        Long orderId = order.getId();
        em.clear();

        // load fresh from DB, verify both lines are there
        Order loaded = em.find(Order.class, orderId);
        assertThat(loaded.getLines()).hasSize(2);

        // remove one line and flush
        OrderLine toRemove = loaded.getLines().get(0);
        Long removedId = toRemove.getId();
        loaded.removeLine(toRemove);
        em.flush();
        em.clear();

        // the removed line must be gone from the DB
        assertThat(em.find(OrderLine.class, removedId)).isNull();
        assertThat(em.find(Order.class, orderId).getLines()).hasSize(1);
    }

    private OrderLine buildLine(String productName) {
        OrderLine line = new OrderLine();
        line.setProductName(productName);
        line.setQuantity(1);
        line.setUnitPrice(new BigDecimal("10.00"));
        line.setLineTotal(new BigDecimal("10.00"));
        return line;
    }
}
