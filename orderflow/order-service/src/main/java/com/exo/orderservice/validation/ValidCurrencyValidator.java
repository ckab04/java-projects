package com.exo.orderservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Currency;
import java.util.Locale;

public class ValidCurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // your logic: hint, java.util.Currency.getInstance(...)

        if(value == null){
            return true;
        }
        try{
            Currency currency = Currency.getInstance(value.toUpperCase(Locale.ROOT));
            return Currency.getAvailableCurrencies().contains(currency);

        }catch (IllegalArgumentException ex){
            return false;
        }

    }

}
