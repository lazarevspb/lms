package ru.gbteam.lms.validator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.TitleValidator;
import ru.gbteam.lms.validator.chain.check.PasswordCheck;
import ru.gbteam.lms.validator.chain.TitleChecker;

import java.util.List;

public class PasswordValidator implements TitleValidator {

    private final TitleChecker passwordChecker = new PasswordCheck();
    Logger logger = LoggerFactory.getLogger(PasswordValidator.class);

    @Override
    public boolean isValid(List<String> wordTitle) {
        logger.info("Password validator entered");
        return passwordChecker.check(wordTitle);
    }
}
