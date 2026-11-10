-- =========================================================================
-- V3__payment_order_id_and_checks.sql
-- Tightens the payment table:
--   - order_id becomes mandatory and indexed (every payment belongs to
--     exactly one order; no FK — database-per-service, order-service
--     owns its own DB)
--   - CHECK constraints enforce business invariants that the nullable
--     single-table-inheritance columns can't express on their own
-- Target: PostgreSQL 14+
-- =========================================================================

ALTER TABLE payment
    ALTER COLUMN order_id SET NOT NULL;

CREATE INDEX idx_payment_order_id ON payment (order_id);

-- amount must be a positive charge
ALTER TABLE payment
    ADD CONSTRAINT ck_payment_amount_positive CHECK (amount > 0);

-- currency must be a 3-letter ISO 4217 code (VARCHAR(3) only caps the length)
ALTER TABLE payment
    ADD CONSTRAINT ck_payment_currency_iso CHECK (char_length(currency) = 3);

-- the populated subclass columns must match the discriminator:
-- CARD rows carry card fields only, BANK_TRANSFER rows carry bank fields only
ALTER TABLE payment
    ADD CONSTRAINT ck_payment_subtype_columns CHECK (
        (payment_type = 'CARD'
            AND card_last4 IS NOT NULL AND card_brand IS NOT NULL
            AND iban IS NULL AND bic IS NULL AND account_holder IS NULL)
        OR
        (payment_type = 'BANK_TRANSFER'
            AND iban IS NOT NULL AND bic IS NOT NULL AND account_holder IS NOT NULL
            AND card_last4 IS NULL AND card_brand IS NULL)
    );
