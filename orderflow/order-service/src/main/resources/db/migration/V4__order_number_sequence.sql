-- =========================================================================
-- V4__order_number_sequence.sql
-- Dedicated sequence for generating human-readable order numbers
-- (date + sequence), kept separate from orders_seq which backs the
-- orders.id primary key.
-- =========================================================================
CREATE SEQUENCE order_number_seq START WITH 1 INCREMENT BY 1;
