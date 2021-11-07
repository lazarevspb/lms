package ru.gbteam.lms.validator.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gbteam.lms.validator.TitleValidator;
import ru.gbteam.lms.validator.chain.check.EnglishCyrillicCharactersCheck;
import ru.gbteam.lms.validator.chain.check.SpaceCheck;
import ru.gbteam.lms.validator.chain.check.SpecialCharactersCheck;
import ru.gbteam.lms.validator.chain.TitleChecker;

import java.util.List;

public class AnyTitleValidator implements TitleValidator {

    protected TitleChecker titleChecker = new SpecialCharactersCheck();
    Logger logger = LoggerFactory.getLogger(AnyTitleValidator.class);

    /*Только латиница и кириллица, двойные пробелы не допускаются*/
    protected TitleChecker commonChain() {
        return titleChecker.linkWith(new SpaceCheck())
                .linkWith(new EnglishCyrillicCharactersCheck());
    }

    @Override
    public boolean isValid(List<String> wordTitle) {
        logger.info("AnyTitleValidator entered");
        commonChain();
        return titleChecker.check(wordTitle);
    }
}
