package ru.gbteam.lms.validator.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.TitleValidator;
import ru.gbteam.lms.validator.chain.check.EmailCheck;
import ru.gbteam.lms.validator.chain.TitleChecker;

import java.util.List;

public class EmailValidator implements TitleValidator {

    private final TitleChecker emailChecker = new EmailCheck();
    Logger logger = LoggerFactory.getLogger(EmailValidator.class);

    @Override
    public boolean isValid(List<String> wordTitle) {
        logger.info("Email validator entered");
        return emailChecker.check(wordTitle);
    }
}
