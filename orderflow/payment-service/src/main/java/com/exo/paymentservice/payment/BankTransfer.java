package com.exo.paymentservice.payment;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("BANK_TRANSFER")
@Setter
@Getter
public class BankTransfer extends Payment {

    @Column(name = "iban", length = 34)
    private String iban;

    @Column(name = "bic", length = 11)
    private String bic;

    @Column(name = "account_holder", length = 128)
    private String accountHolderName;

}
