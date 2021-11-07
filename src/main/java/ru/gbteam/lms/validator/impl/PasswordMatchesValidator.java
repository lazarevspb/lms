package ru.gbteam.lms.validator.impl;

import ru.gbteam.lms.dto.UserWithPwdDto;
import ru.gbteam.lms.annotation.PasswordMatches;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserWithPwdDto user = (UserWithPwdDto) obj;
        if(Objects.isNull(user.getPassword())){
            return true;
        }
        return user.getPassword().equals(user.getMatchingPassword());
    }
}