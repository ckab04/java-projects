package com.exo.orderservice.customer;

import com.exo.orderservice.common.Address;
import com.exo.orderservice.common.CommonValues;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends CommonValues {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private LocalDate dateOfBirth;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "recipientName", column = @Column(name = "address_recipient_name")),
        @AttributeOverride(name = "streetLine",    column = @Column(name = "address_street_line")),
        @AttributeOverride(name = "city",          column = @Column(name = "address_city")),
        @AttributeOverride(name = "postalCode",    column = @Column(name = "address_postal_code")),
        @AttributeOverride(name = "country",       column = @Column(name = "address_country"))
    })
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer other)) return false;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Hibernate.getClass(this).hashCode();
    }
}
