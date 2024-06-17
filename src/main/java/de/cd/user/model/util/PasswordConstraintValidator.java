package de.cd.user.model.util;

import de.cd.user.model.exceptions.PasswordNotValidException;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * Valid UserPasswordValidator class
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidUserPassword, String> {


    @Override
    public void initialize(ValidUserPassword arg0) {
    }

    /**
     * Checks whether the new Password complies to rules specified in validator
     *
     * @param password String
     * @param context  ConstraintValidatorContext
     * @return Boolean
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(6, 25),
                new CharacterRule(GermanCharacterData.UpperCase, 1),
                new CharacterRule(GermanCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        throw new PasswordNotValidException(validator.getMessages(result).toString());
    }
}
