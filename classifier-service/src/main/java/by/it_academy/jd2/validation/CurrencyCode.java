package by.it_academy.jd2.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CurrencyNameValidator.class)
public @interface CurrencyCode {

    String message() default "Неверный код валюты";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
