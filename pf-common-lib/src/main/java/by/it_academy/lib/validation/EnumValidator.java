package by.it_academy.lib.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class EnumValidator implements ConstraintValidator<EnumValue, String> {

    private List<String> acceptedValues;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.isBlank()) {
            return true;
        }

        return acceptedValues.contains(s);
    }
}

