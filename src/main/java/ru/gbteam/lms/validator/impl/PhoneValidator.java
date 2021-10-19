package ru.gbteam.lms.validator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.TitleValidator;
import ru.gbteam.lms.validator.chain.check.PhoneCheck;
import ru.gbteam.lms.validator.chain.TitleChecker;

import java.util.List;

public class PhoneValidator implements TitleValidator {

    private final TitleChecker phoneChecker = new PhoneCheck();
    Logger logger = LoggerFactory.getLogger(PhoneValidator.class);

    @Override
    public boolean isValid(List<String> wordTitle) {
        logger.info("Phone validator entered");
        return phoneChecker.check(wordTitle);
    }
}
