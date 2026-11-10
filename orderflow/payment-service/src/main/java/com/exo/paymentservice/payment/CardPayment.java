package com.exo.paymentservice.payment;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("CARD")
@Setter
@Getter
public class CardPayment extends Payment{

    @Column(name = "card_last4", length = 4)
    private String cardLast4;

    @Column(name = "card_brand", length = 32)
    private String cardBrand;        // VISA, MASTERCARD...


}
