package com.exo.paymentservice;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // don't swap to H2/embedded
@Testcontainers
class PaymentRepositoryIT {

    @Container
    @ServiceConnection                              // auto-wires spring.datasource.* from the container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine");

    //@Autowired
    //ProductRepository repository;

    @Test
    void flywayMigratedSchemaIsValidAndWritable() {
        // If we get here, Flyway ran the migrations AND ddl-auto=validate passed.
       /* Product saved = repository.save(newProduct("Keyboard", "49.99"));

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId()))
                .isPresent()
                .get()
                .extracting(Product::getName)
                .isEqualTo("Keyboard"); */
    }

   /* private Product newProduct(String name, String price) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(new BigDecimal(price));
        return p;
    } */
}