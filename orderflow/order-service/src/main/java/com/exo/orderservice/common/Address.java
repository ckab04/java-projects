package com.exo.orderservice.common;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Address  {

    @Column(name = "recipient_name", length = 255)
    private String recipientName;

    @Column(name = "street_line", length = 255)
    private String streetLine;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "country", length = 2)
    private String country;
}
