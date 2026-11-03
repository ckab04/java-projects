-- =========================================================================
-- V2__create_orders_schema.sql
-- Creates: customers, addresses, orders, order_lines
-- Target: PostgreSQL 14+
-- =========================================================================

-- -------------------------------------------------------------------------
-- SEQUENCES  (allocation size 50 matches Hibernate 6 default)
-- -------------------------------------------------------------------------
CREATE SEQUENCE customers_seq   START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE orders_seq      START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE order_lines_seq START WITH 1 INCREMENT BY 50;

-- -------------------------------------------------------------------------
-- CUSTOMER
-- -------------------------------------------------------------------------
CREATE TABLE customers (
                           id                BIGINT       PRIMARY KEY,
                           first_name        VARCHAR(100) NOT NULL,
                           last_name         VARCHAR(100) NOT NULL,
                           email             VARCHAR(255) NOT NULL,
                           date_of_birth     DATE,

                    -- embedded Address (prefix: address_)
                           address_recipient_name  VARCHAR(255),
                           address_street_line     VARCHAR(255),
                           address_city            VARCHAR(100),
                           address_postal_code     VARCHAR(20),
                           address_country         VARCHAR(2),


                           created_at        TIMESTAMPTZ    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at        TIMESTAMPTZ    NOT NULL DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT uq_customers_email UNIQUE (email)
);

-- -------------------------------------------------------------------------
-- ORDERS  ("order" is reserved — table name is plural)
-- Embedded shipping address is flattened into columns.
-- -------------------------------------------------------------------------
CREATE TABLE orders (
                        id                      BIGINT       PRIMARY KEY,
                        customer_id             BIGINT       NOT NULL,
                        order_number            VARCHAR(40)  NOT NULL,
                        order_date              TIMESTAMPTZ    NOT NULL,
                        shipped_date            TIMESTAMPTZ,
                        delivered_date          TIMESTAMPTZ,
                        status                  VARCHAR(20)  NOT NULL,    -- OrderStatus enum name
                        subtotal                NUMERIC(19,4) NOT NULL,

                        total_amount            NUMERIC(19,4) NOT NULL,
                        currency                VARCHAR(3)   NOT NULL,    -- ISO 4217
                        payment_method          VARCHAR(50),


    -- Embedded shipping address (flattened)
                        shipping_recipient_name VARCHAR(255),
                        shipping_street_line   VARCHAR(255),
                        shipping_city           VARCHAR(100),
                        shipping_postal_code    VARCHAR(20),
                        shipping_country        VARCHAR(2),

                        created_at              TIMESTAMPTZ    NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at              TIMESTAMPTZ    NOT NULL DEFAULT CURRENT_TIMESTAMP,

                        CONSTRAINT uq_orders_order_number UNIQUE (order_number),
                        CONSTRAINT fk_orders_customer
                            FOREIGN KEY (customer_id) REFERENCES customers (id)
);

CREATE INDEX idx_orders_customer_id ON orders (customer_id);
CREATE INDEX idx_orders_status      ON orders (status);
CREATE INDEX idx_orders_order_date  ON orders (order_date);

-- -------------------------------------------------------------------------
-- ORDER_LINES
-- -------------------------------------------------------------------------
CREATE TABLE order_lines (
                             id                BIGINT        PRIMARY KEY,
                             order_id          BIGINT        NOT NULL,
                             product_id        BIGINT,
                             product_name      VARCHAR(255)  NOT NULL,
                             product_description VARCHAR(1000),
                             quantity          INTEGER       NOT NULL,
                             unit_price        NUMERIC(19,4) NOT NULL,
                             line_total        NUMERIC(19,4) NOT NULL,

                             created_at        TIMESTAMPTZ     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at        TIMESTAMPTZ     NOT NULL DEFAULT CURRENT_TIMESTAMP,

                             CONSTRAINT fk_order_lines_order
                                 FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
                             CONSTRAINT ck_order_lines_quantity CHECK (quantity > 0)
);

CREATE INDEX idx_order_lines_order_id ON order_lines (order_id);