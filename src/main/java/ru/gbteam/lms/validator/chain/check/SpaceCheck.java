package ru.gbteam.lms.validator.chain.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.chain.TitleChecker;

import java.util.List;

public class SpaceCheck extends TitleChecker {

    Logger logger = LoggerFactory.getLogger(SpaceCheck.class);

    private static boolean isExtraSpace(String s) {
        return s.equals("");
    }

    /**
     * Слова в заголовках разделяются одним пробелом Строка не должна начинаться с пробела
     */
    public boolean check(List<String> wordTitle) {
        logger.debug("search for spaces...");
        if (wordTitle.get(0).startsWith(" ") || wordTitle.stream().anyMatch(SpaceCheck::isExtraSpace)) {
            logger.info("SpaceCheck - EXTRA SPACES FOUND");
            return false;
        }
        return checkNext(wordTitle);
    }
}
