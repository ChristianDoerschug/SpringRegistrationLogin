package de.cd.user.inbound.DTO;

import de.cd.user.model.entities.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminRegistrationDTOTest {

    private AdminRegistrationDTO adminRegistrationDTO;
    private final String EMAIL = "cd875293@fh-muenster.de";
    private final String USERNAME = "Christian";


    @Test
    public void testConstructor() {
        assertEquals(Role.ADMIN, (new AdminRegistrationDTO()).getRole());
    }

    @BeforeEach
    public void setUp() {
        this.adminRegistrationDTO = new AdminRegistrationDTO(1L, EMAIL, USERNAME, "!Password123", "Christian", "Doerschug",
                new Date(), "Germany", "Muenster", "Gemenweg", 174);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
        assertEquals(1L, adminRegistrationDTO.getId());
    }

    @Test
    void setId() {
        adminRegistrationDTO.setId(2);
        assertEquals(2, adminRegistrationDTO.getId());
    }

    @Test
    void getEmail() {
        assertEquals(EMAIL, adminRegistrationDTO.getEmail());
    }

    @Test
    void setEmail() {
        adminRegistrationDTO.setEmail("test@test.de");
        assertEquals("test@test.de", adminRegistrationDTO.getEmail());
    }

    @Test
    void getUsername() {
        assertEquals(USERNAME, adminRegistrationDTO.getUsername());
    }

    @Test
    void setUsername() {
        adminRegistrationDTO.setUsername("test");
        assertEquals("test", adminRegistrationDTO.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("!Password123", adminRegistrationDTO.getPassword());
    }

    @Test
    void setPassword() {
        adminRegistrationDTO.setPassword("test");
        assertEquals("test", adminRegistrationDTO.getPassword());
    }

    @Test
    void getRole() {
        assertEquals(Role.ADMIN, adminRegistrationDTO.getRole());
    }

    @Test
    void isActivated() {
        assertEquals(true, adminRegistrationDTO.isActivated());
    }

    @Test
    void getCreated() {
        LocalDateTime.now().toLocalDate().compareTo(adminRegistrationDTO.getCreated().toLocalDate());
    }

    @Test
    void getEdited() {
        LocalDateTime.now().toLocalDate().compareTo(adminRegistrationDTO.getEdited().toLocalDate());
    }

    @Test
    void getFirstName() {
        assertEquals("Christian", adminRegistrationDTO.getFirstName());
    }

    @Test
    void setFirstName() {
        adminRegistrationDTO.setFirstName("Test");
        assertEquals("Test", adminRegistrationDTO.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doerschug", adminRegistrationDTO.getLastName());
    }

    @Test
    void setLastName() {
        adminRegistrationDTO.setLastName("test");
        assertEquals("test", adminRegistrationDTO.getLastName());
    }

    @Test
    void getBirthday() {
        new Date().compareTo(adminRegistrationDTO.getBirthday());
    }

    @Test
    void setBirthday() {
        adminRegistrationDTO.setBirthday(new Date());
        assertEquals(new Date(), adminRegistrationDTO.getBirthday());
    }

    @Test
    void getCountry() {
        assertEquals("Germany", adminRegistrationDTO.getCountry());
    }

    @Test
    void setCountry() {
        adminRegistrationDTO.setCountry("test");
        assertEquals("test", adminRegistrationDTO.getCountry());
    }

    @Test
    void getCity() {
        assertEquals("Muenster", adminRegistrationDTO.getCity());
    }

    @Test
    void setCity() {
        adminRegistrationDTO.setCity("test");
        assertEquals("test", adminRegistrationDTO.getCity());
    }

    @Test
    void getStreet() {
        assertEquals("Gemenweg", adminRegistrationDTO.getStreet());
    }

    @Test
    void setStreet() {
        adminRegistrationDTO.setStreet("test");
        assertEquals("test", adminRegistrationDTO.getStreet());
    }

    @Test
    void getHouseNo() {
        assertEquals(174, adminRegistrationDTO.getHouseNo());
    }

    @Test
    void setHouseNo() {
        adminRegistrationDTO.setHouseNo(1);
        assertEquals(1, adminRegistrationDTO.getHouseNo());
    }
}