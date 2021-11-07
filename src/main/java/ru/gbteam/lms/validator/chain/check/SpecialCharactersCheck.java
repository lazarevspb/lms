package ru.gbteam.lms.validator.chain.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.chain.TitleChecker;

import java.util.List;

public class SpecialCharactersCheck extends TitleChecker {

    Logger logger = LoggerFactory.getLogger(SpecialCharactersCheck.class);

    private static boolean isSpecialCharacter(String s) {
        return (s.equals("\\r") || s.equals("\\t") || s.equals("\\n"));
    }

    /**
     * Символы \r, \t, \n, являются запрещенными
     */
    public boolean check(List<String> wordTitle) {
        logger.info("search for special characters...");
        if (wordTitle.stream().anyMatch(SpecialCharactersCheck::isSpecialCharacter)) {
            logger.info("SpecialCharactersCheck - SPECIAL CHARACTERS FOUND");
            return false;
        }
        return checkNext(wordTitle);
    }
}
