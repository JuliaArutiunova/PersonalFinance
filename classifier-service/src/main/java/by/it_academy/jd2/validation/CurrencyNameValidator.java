package by.it_academy.jd2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Currency;

public class CurrencyNameValidator implements ConstraintValidator<CurrencyCode, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Currency.getInstance(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
