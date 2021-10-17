package ru.gbteam.lms.validator.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.TitleValidator;
import ru.gbteam.lms.validator.chain.check.LoginCheck;
import ru.gbteam.lms.validator.chain.TitleChecker;

import java.util.List;

public class LoginValidator implements TitleValidator {

    private final TitleChecker loginChecker = new LoginCheck();
    Logger logger = LoggerFactory.getLogger(LoginValidator.class);

    @Override
    public boolean isValid(List<String> wordTitle) {
        logger.info("Login validator entered");
        return loginChecker.check(wordTitle);
    }
}
