package com.exo.orderservice.order;

import com.exo.orderservice.common.Address;
import com.exo.orderservice.common.CommonValues;
import com.exo.orderservice.common.OrderStatus;
import com.exo.orderservice.customer.Customer;
import com.exo.orderservice.orderline.OrderLine;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends CommonValues {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String orderNumber;
    private Instant orderDate;
    private Instant shippedDate;
    private Instant deliveredDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(precision = 19, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 3)
    private String currency;

    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<OrderLine> lines = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "recipientName", column = @Column(name = "shipping_recipient_name")),
        @AttributeOverride(name = "streetLine",    column = @Column(name = "shipping_street_line")),
        @AttributeOverride(name = "city",          column = @Column(name = "shipping_city")),
        @AttributeOverride(name = "postalCode",    column = @Column(name = "shipping_postal_code")),
        @AttributeOverride(name = "country",       column = @Column(name = "shipping_country"))
    })
    private Address address;

    public List<OrderLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order other)) return false;
        return orderNumber != null && orderNumber.equals(other.getOrderNumber());
    }

    @Override
    public int hashCode() {
        return Hibernate.getClass(this).hashCode();
    }

    public void addLine(OrderLine line) {
        lines.add(line);
        line.setOrder(this);
    }

    public void removeLine(OrderLine line) {
        lines.remove(line);
        line.setOrder(null);
    }
}
