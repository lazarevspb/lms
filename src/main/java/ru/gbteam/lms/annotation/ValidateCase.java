package ru.gbteam.lms.annotation;


import ru.gbteam.lms.validator.TitleCaseValidator;
import ru.gbteam.lms.enums.ValidateType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TitleCaseValidator.class)
public @interface ValidateCase {

    ValidateType type() default ValidateType.ANY;

    String message() default "Ошибка ввода!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
