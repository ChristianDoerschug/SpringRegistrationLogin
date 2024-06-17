package de.cd.user.model.util;

import de.cd.user.model.exceptions.PasswordNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdminPasswordConstraintValidatorTest {

    @Test
    public void testIsValid() {
        assertThrows(PasswordNotValidException.class,
                () -> (new AdminPasswordConstraintValidator()).isValid("iloveyou", null));
    }
}

