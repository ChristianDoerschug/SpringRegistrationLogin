package de.cd.user.model.entities;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import de.cd.user.inbound.DTO.UserGetDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


class TokenTest {

    @Test
    public void testConstructor() {
        Token actualToken = new Token();
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualToken.setConfirmedAt(ofResult);
        LocalDateTime ofResult1 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualToken.setCreatedAt(ofResult1);
        LocalDateTime ofResult2 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualToken.setExpiresAt(ofResult2);
        actualToken.setId(1L);
        actualToken.setToken("ABC123");
        User user = new User();
        user.setLastName("Test");
        user.setTokens(new ArrayList<Token>());
        user.setEmail("Alex.Test@example.org");
        user.setPassword("Test123");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthday(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        user.setRole(Role.ADMIN);
        user.setId(1L);
        user.setFirstName("Alex");
        user.setHouseNo(1);
        user.setActivated(true);
        user.setPasswordHash("Password Hash");
        LocalDateTime ofResult3 = LocalDateTime.of(1, 1, 1, 1, 1);
        user.setCreated(ofResult3);
        user.setUsername("AlexTest");
        user.setCountry("Country");
        LocalDateTime ofResult4 = LocalDateTime.of(1, 1, 1, 1, 1);
        user.setEdited(ofResult4);
        user.setCity("Oxford");
        user.setId((Long) 1L);
        user.setStreet("Street");
        actualToken.setUser(user);
        LocalDateTime confirmedAt = actualToken.getConfirmedAt();
        assertEquals(ofResult3, confirmedAt);
        assertEquals(ofResult1, confirmedAt);
        assertEquals(ofResult2, confirmedAt);
        assertSame(ofResult, confirmedAt);
        assertEquals(ofResult4, confirmedAt);
        LocalDateTime createdAt = actualToken.getCreatedAt();
        assertEquals(ofResult2, createdAt);
        assertEquals(ofResult4, createdAt);
        assertSame(ofResult1, createdAt);
        assertEquals(ofResult3, createdAt);
        assertEquals(confirmedAt, createdAt);
        LocalDateTime expiresAt = actualToken.getExpiresAt();
        assertEquals(confirmedAt, expiresAt);
        assertEquals(createdAt, expiresAt);
        assertSame(ofResult2, expiresAt);
        assertEquals(ofResult4, expiresAt);
        assertEquals(ofResult3, expiresAt);
        assertEquals(1L, actualToken.getId().longValue());
        assertEquals("ABC123", actualToken.getToken());
        assertSame(user, actualToken.getUser());
    }

    /*@Test
    public void testConstructor2() {
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime ofResult1 = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime ofResult2 = LocalDateTime.of(1, 1, 1, 1, 1);
        Token actualToken = new Token("ABC123", ofResult, ofResult1, ofResult2);
        LocalDateTime ofResult3 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualToken.setConfirmedAt(ofResult3);
        LocalDateTime ofResult4 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualToken.setCreatedAt(ofResult4);
        LocalDateTime ofResult5 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualToken.setExpiresAt(ofResult5);
        actualToken.setId(1L);
        actualToken.setToken("ABC123");
        User user = new User();
        user.setLastName("Test");
        user.setTokens(new ArrayList<Token>());
        user.setEmail("Alex.Test@example.org");
        user.setPassword("Test123");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthday(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        user.setRole(Role.ADMIN);
        user.setId(1L);
        user.setFirstName("Alex");
        user.setHouseNo(1);
        user.setActivated(true);
        user.setPasswordHash("Password Hash");
        LocalDateTime ofResult6 = LocalDateTime.of(1, 1, 1, 1, 1);
        user.setCreated(ofResult6);
        user.setUsername("AlexTest");
        user.setCountry("Country");
        LocalDateTime ofResult7 = LocalDateTime.of(1, 1, 1, 1, 1);
        user.setEdited(ofResult7);
        user.setCity("Oxford");
        user.setId((Long) 1L);
        user.setStreet("Street");
        actualToken.setUser(user);
        LocalDateTime confirmedAt = actualToken.getConfirmedAt();
        assertEquals(ofResult6, confirmedAt);
        assertEquals(ofResult4, confirmedAt);
        assertEquals(ofResult1, confirmedAt);
        assertEquals(ofResult7, confirmedAt);
        assertEquals(ofResult2, confirmedAt);
        assertSame(ofResult3, confirmedAt);
        assertEquals(ofResult5, confirmedAt);
        assertEquals(ofResult, confirmedAt);
        LocalDateTime createdAt = actualToken.getCreatedAt();
        assertSame(ofResult4, createdAt);
        assertEquals(ofResult, createdAt);
        assertEquals(ofResult2, createdAt);
        assertEquals(confirmedAt, createdAt);
        assertEquals(ofResult1, createdAt);
        assertEquals(ofResult5, createdAt);
        assertEquals(ofResult6, createdAt);
        assertEquals(ofResult7, createdAt);
        LocalDateTime expiresAt = actualToken.getExpiresAt();
        assertEquals(ofResult1, expiresAt);
        assertEquals(confirmedAt, expiresAt);
        assertEquals(ofResult7, expiresAt);
        assertSame(ofResult5, expiresAt);
        assertEquals(createdAt, expiresAt);
        assertEquals(ofResult2, expiresAt);
        assertEquals(ofResult, expiresAt);
        assertEquals(ofResult6, expiresAt);
        assertEquals(1L, actualToken.getId().longValue());
        assertEquals("ABC123", actualToken.getToken());
        assertSame(user, actualToken.getUser());
    }

    @Test
    public void testConstructor3() {
        User user = new User();
        Token actualToken = new Token("ABC123", user);

        assertSame(user, actualToken.getUser());
        assertEquals("ABC123", actualToken.getToken());
    }
*/
    @Test
    public void validateSettersAndGetters() {
        PojoClass personPojo = PojoClassFactory.getPojoClass(UserGetDto.class);
        Validator validator = ValidatorBuilder.create()
                // Lets make sure that we have a getter and a setter for every field defined.
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                // Lets also validate that they are behaving as expected
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

        // Start the Test
        validator.validate(personPojo);
    }

}