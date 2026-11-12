-- =========================================================================
-- V4__amount_scale_2.sql
-- Standardizes payment.amount on scale 2 (matches order-service's
-- subtotal/total_amount/unit_price/line_total). Payment amounts come
-- from the order total, so the two must agree on scale or the same
-- value round-trips differently between services.
-- Previously NUMERIC(19,4).
-- =========================================================================

ALTER TABLE payment
    ALTER COLUMN amount TYPE NUMERIC(19,2);
