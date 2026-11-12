-- =========================================================================
-- V3__money_scale_2.sql
-- Standardizes money columns on scale 2 (matches real-world currency
-- minor units and the BigDecimal scale the service computes with).
-- Previously NUMERIC(19,4), which made GET responses show trailing
-- zeros (29.9700) that never appeared in the POST response (29.97).
-- =========================================================================

ALTER TABLE orders
    ALTER COLUMN subtotal TYPE NUMERIC(19,2),
    ALTER COLUMN total_amount TYPE NUMERIC(19,2);

ALTER TABLE order_lines
    ALTER COLUMN unit_price TYPE NUMERIC(19,2),
    ALTER COLUMN line_total TYPE NUMERIC(19,2);
