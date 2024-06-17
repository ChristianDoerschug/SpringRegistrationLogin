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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserCrucialInfosDTOTest {

    @Test
    public void testConstructor() {
        UserCrucialInfosDTO actualUserCrucialInfosDTO = new UserCrucialInfosDTO();
        actualUserCrucialInfosDTO.setActivated(true);
        actualUserCrucialInfosDTO.setEmail("Alex.Test@example.org");
        actualUserCrucialInfosDTO.setRole(Role.ADMIN);
        actualUserCrucialInfosDTO.setUsername("AlexTest");
        assertEquals("Alex.Test@example.org", actualUserCrucialInfosDTO.getEmail());
        assertEquals(Role.ADMIN, actualUserCrucialInfosDTO.getRole());
        assertEquals("AlexTest", actualUserCrucialInfosDTO.getUsername());
        assertTrue(actualUserCrucialInfosDTO.isActivated());
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