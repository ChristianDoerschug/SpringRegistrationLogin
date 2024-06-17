package de.cd.user.inbound.DTO;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import de.cd.user.model.entities.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class UserRegistrationDTOTest {

    @Test
    public void testConstructor() {
        UserRegistrationDTO actualUserRegistrationDTO = new UserRegistrationDTO();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        actualUserRegistrationDTO.setBirthday(fromResult);
        actualUserRegistrationDTO.setCity("Berlin");
        actualUserRegistrationDTO.setCountry("Country");
        actualUserRegistrationDTO.setEmail("Alex.Test@example.org");
        actualUserRegistrationDTO.setFirstName("Alex");
        actualUserRegistrationDTO.setHouseNo(1);
        actualUserRegistrationDTO.setId(1L);
        actualUserRegistrationDTO.setLastName("Doe");
        actualUserRegistrationDTO.setPassword("Test123");
        actualUserRegistrationDTO.setStreet("Street");
        actualUserRegistrationDTO.setUsername("AlexTest");
        assertSame(fromResult, actualUserRegistrationDTO.getBirthday());
        assertEquals("Berlin", actualUserRegistrationDTO.getCity());
        assertEquals("Country", actualUserRegistrationDTO.getCountry());
        LocalDateTime.now().toLocalDate().compareTo(actualUserRegistrationDTO.getCreated().toLocalDate());
        LocalDateTime.now().toLocalDate().compareTo(actualUserRegistrationDTO.getEdited().toLocalDate());
        assertEquals("Alex.Test@example.org", actualUserRegistrationDTO.getEmail());
        assertEquals("Alex", actualUserRegistrationDTO.getFirstName());
        assertEquals(1, actualUserRegistrationDTO.getHouseNo());
        assertEquals(1L, actualUserRegistrationDTO.getId());
        assertEquals("Doe", actualUserRegistrationDTO.getLastName());
        assertEquals("Test123", actualUserRegistrationDTO.getPassword());
        assertEquals(Role.USER, actualUserRegistrationDTO.getRole());
        assertEquals("Street", actualUserRegistrationDTO.getStreet());
        assertEquals("AlexTest", actualUserRegistrationDTO.getUsername());
        assertFalse(actualUserRegistrationDTO.isActivated());
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