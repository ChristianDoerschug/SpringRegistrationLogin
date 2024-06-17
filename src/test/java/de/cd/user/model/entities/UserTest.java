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

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    @Test
    public void testConstructor() {
        User actualUser = new User();
        actualUser.setActivated(true);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        actualUser.setBirthday(fromResult);
        actualUser.setCity("Berlin");
        actualUser.setCountry("Country");
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        actualUser.setCreated(ofResult);
        LocalDateTime ofResult1 = LocalDateTime.of(1, 1, 1, 1, 1);
        actualUser.setEdited(ofResult1);
        actualUser.setEmail("Alex.Test@example.org");
        actualUser.setFirstName("Alex");
        actualUser.setHouseNo(1);
        actualUser.setId((Long) 123L);
        actualUser.setLastName("Test");
        actualUser.setPassword("Test123");
        actualUser.setPasswordHash("Password Hash");
        actualUser.setRole(Role.ADMIN);
        actualUser.setStreet("Street");
        ArrayList<Token> tokenList = new ArrayList<Token>();
        actualUser.setTokens(tokenList);
        actualUser.setUsername("AlexTest");
        assertSame(fromResult, actualUser.getBirthday());
        assertEquals("Berlin", actualUser.getCity());
        assertEquals("Country", actualUser.getCountry());
        LocalDateTime created = actualUser.getCreated();
        assertEquals(ofResult1, created);
        assertSame(ofResult, created);
        LocalDateTime edited = actualUser.getEdited();
        assertEquals(created, edited);
        assertSame(ofResult1, edited);
        assertEquals("Alex.Test@example.org", actualUser.getEmail());
        assertEquals("Alex", actualUser.getFirstName());
        assertEquals(1, actualUser.getHouseNo());
        assertEquals("Test", actualUser.getLastName());
        assertEquals("Test123", actualUser.getPassword());
        assertEquals("Password Hash", actualUser.getPasswordHash());
        assertEquals(Role.ADMIN, actualUser.getRole());
        assertEquals("Street", actualUser.getStreet());
        assertSame(tokenList, actualUser.getTokens());
        assertEquals("AlexTest", actualUser.getUsername());
        assertTrue(actualUser.isActivated());
    }

    @Test
    public void testSetId() {
        User user = new User();
        user.setId(123L);
        assertEquals(123L, user.getId());
    }

    @Test
    public void testGetId() {
        User user = new User();
        user.setId(123L);
        assertEquals(123L, user.getId());
    }

    @Test
    public void testSetMedia() {
        User user = new User();
        user.setMedia(1);
        assertEquals(1, user.getMedia());
    }

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