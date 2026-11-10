package com.exo.paymentservice;

import com.exo.paymentservice.payment.BankTransfer;
import com.exo.paymentservice.payment.CardPayment;
import com.exo.paymentservice.payment.Payment;
import com.exo.paymentservice.payment.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostgresContainerConfig.class)
class PaymentDiscriminatorTest {

    @Autowired
    TestEntityManager em;

    @Test
    void reloadingPaymentsThroughBaseTypeReturnsCorrectSubclass() {
        CardPayment card = new CardPayment();
        card.setOrderId(1L);
        card.setAmount(new BigDecimal("50.00"));
        card.setCurrency("EUR");
        card.setStatus(PaymentStatus.COMPLETED);
        card.setCardLast4("4242");
        card.setCardBrand("VISA");

        BankTransfer transfer = new BankTransfer();
        transfer.setOrderId(2L);
        transfer.setAmount(new BigDecimal("120.00"));
        transfer.setCurrency("EUR");
        transfer.setStatus(PaymentStatus.PENDING);
        transfer.setIban("DE89370400440532013000");
        transfer.setBic("COBADEFFXXX");
        transfer.setAccountHolderName("Jane Doe");

        em.persistAndFlush(card);
        em.persistAndFlush(transfer);
        Long cardId = card.getId();
        Long transferId = transfer.getId();
        em.clear();

        List<Payment> payments = em.getEntityManager()
                .createQuery("select p from Payment p", Payment.class)
                .getResultList();

        assertThat(payments).hasSize(2);

        Payment loadedCard = payments.stream()
                .filter(p -> p.getId().equals(cardId))
                .findFirst()
                .orElseThrow();
        Payment loadedTransfer = payments.stream()
                .filter(p -> p.getId().equals(transferId))
                .findFirst()
                .orElseThrow();

        assertThat(loadedCard).isInstanceOf(CardPayment.class);
        assertThat(((CardPayment) loadedCard).getCardLast4()).isEqualTo("4242");

        assertThat(loadedTransfer).isInstanceOf(BankTransfer.class);
        assertThat(((BankTransfer) loadedTransfer).getIban()).isEqualTo("DE89370400440532013000");
    }
}
