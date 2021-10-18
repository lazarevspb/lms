package ru.gbteam.lms.validator.chain.check;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.chain.TitleChecker;

import java.util.List;

public class EnglishCyrillicCharactersCheck extends TitleChecker {

    Logger logger = LoggerFactory.getLogger(EnglishCyrillicCharactersCheck.class);

    private static boolean isEnglishCyrillicCharacters(String s) {
        String lowerCaseWord = s.toLowerCase();
        return !(lowerCaseWord.matches("[a-z\\.\",:]+") || lowerCaseWord.matches("[а-яё]+"));
    }

    /**
     * Смешение русских и английских символов в строке является недопустимым ", ', ,, : не принадлежат
     * ни к какому языку и допускаются к использованию в любых заголовках. Т.к. они пишутся вплотную к
     * словам, для простоты можем считать их частью алфавита. Любые другие небуквенные символы
     * являются запрещенными
     */
    public boolean check(List<String> wordTitle) {
        logger.info("search for a mixture of cyrillic and latin...");
        if (wordTitle.stream()
                .filter(s -> s.length() > 0)
                .anyMatch(EnglishCyrillicCharactersCheck::isEnglishCyrillicCharacters)) {
            logger.debug(
                    "SpecialCharactersCheck - FOUND LATIN AND CYRILLIC CHARACTERS IN ONE WORD");
            return false;
        }
        return checkNext(wordTitle);
    }
}
