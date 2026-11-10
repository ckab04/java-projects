-- =========================================================================
-- V2__create_payment_table.sql
-- Creates: payment (single-table inheritance: CardPayment, BankTransfer)
-- Target: PostgreSQL 14+
-- =========================================================================

-- -------------------------------------------------------------------------
-- SEQUENCE  (allocation size 50 matches Hibernate 6 default)
-- -------------------------------------------------------------------------
CREATE SEQUENCE payment_seq START WITH 1 INCREMENT BY 50;

-- -------------------------------------------------------------------------
-- PAYMENT
-- payment_type discriminates between CARD and BANK_TRANSFER rows; the
-- subclass-specific columns are nullable since only one set applies
-- per row.
-- -------------------------------------------------------------------------
CREATE TABLE payment (
    id              BIGINT        PRIMARY KEY,
    payment_type    VARCHAR(31)   NOT NULL,
    order_id        BIGINT,

    amount          NUMERIC(19,4) NOT NULL,
    currency        VARCHAR(3)    NOT NULL,
    status          VARCHAR(20)   NOT NULL,    -- PaymentStatus enum name

    -- CardPayment
    card_last4      VARCHAR(4),
    card_brand      VARCHAR(32),

    -- BankTransfer
    iban            VARCHAR(34),
    bic             VARCHAR(11),
    account_holder  VARCHAR(128),

    created_at      TIMESTAMPTZ   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_payment_payment_type ON payment (payment_type);
CREATE INDEX idx_payment_status       ON payment (status);
