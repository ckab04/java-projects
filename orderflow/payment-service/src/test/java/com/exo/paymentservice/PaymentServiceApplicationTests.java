package com.exo.paymentservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PostgresContainerConfig.class)
class PaymentServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
