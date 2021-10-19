package ru.gbteam.lms.validator.chain.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.chain.TitleChecker;

import java.util.List;

public class PasswordCheck extends TitleChecker {

    Logger logger = LoggerFactory.getLogger(PasswordCheck.class);

    private static boolean isSpecialCharacter(String s) {
        String lowerCaseWord = s.toLowerCase();
        return !(lowerCaseWord.matches(
                "([A-Za-z0-9-@#$%^&+=]+)"));
    }

    /**
     * На поле пароль должна быть валидация: латиница, спецсимволы, не менее 8 символов.
     */
    public boolean check(List<String> wordTitle) {
        logger.info("validation password...");
        if (wordTitle.stream().anyMatch(PasswordCheck::isSpecialCharacter)) {
            logger.info("PasswordCheck - INCORRECT PASSWORD FORMAT");
            return false;
        }
        return checkNext(wordTitle);
    }
}
