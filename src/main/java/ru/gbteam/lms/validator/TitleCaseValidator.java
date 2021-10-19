package ru.gbteam.lms.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.impl.*;
import ru.gbteam.lms.annotation.ValidateCase;
import ru.gbteam.lms.enums.ValidateType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TitleCaseValidator implements ConstraintValidator<ValidateCase, String> {

    Logger logger = LoggerFactory.getLogger(TitleCaseValidator.class);
    private TitleValidator titleValidator;

    @Override
    public void initialize(ValidateCase constraintAnnotation) {
        ValidateType type = constraintAnnotation.type();
        if (type == ValidateType.ANY) {
            titleValidator = new AnyTitleValidator();
            logger.debug("Any initialize");
        } else if (type == ValidateType.EMAIL) {
            titleValidator = new EmailValidator();
            logger.info("Email validation initialize");
        } else if (type == ValidateType.LOGIN) {
            titleValidator = new LoginValidator();
            logger.info("Login validation initialize");
        } else if (type == ValidateType.PASSWORD) {
            titleValidator = new PasswordValidator();
            logger.info("Password validation initialize");
        } else if (type == ValidateType.PHONE) {
            titleValidator = new PhoneValidator();
            logger.info("Phone validation initialize");
        } else {
            logger.info("Could not find this type of validation");
        }
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        final List<String> titleWords = Arrays.stream(title.split(" ")).collect(Collectors.toList());
        return validation(titleWords);
    }

    private boolean validation(List<String> titleWords) {
        logger.debug("titleWords = " + titleWords);
        return titleValidator.isValid(titleWords);
    }
}
