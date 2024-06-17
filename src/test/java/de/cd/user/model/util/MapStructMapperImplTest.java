package de.cd.user.model.util;

import de.cd.user.inbound.DTO.*;
import de.cd.user.model.entities.Role;
import de.cd.user.model.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MapStructMapperImpl.class})
@ExtendWith(SpringExtension.class)
public class MapStructMapperImplTest {
    @Autowired
    private MapStructMapperImpl mapStructMapperImpl;


    @Test
    public void testUserGetDto() {
        User user = mock(User.class);
        when(user.getHouseNo()).thenReturn(1);
        when(user.getStreet()).thenReturn("foo");
        when(user.getCity()).thenReturn("foo");
        when(user.getCountry()).thenReturn("foo");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        when(user.getBirthday()).thenReturn(fromResult);
        when(user.getLastName()).thenReturn("foo");
        when(user.getFirstName()).thenReturn("foo");
        when(user.isActivated()).thenReturn(true);
        when(user.getUsername()).thenReturn("foo");
        when(user.getEmail()).thenReturn("foo");
        UserGetDto actualUserGetDtoResult = this.mapStructMapperImpl.userGetDto(user);
        assertSame(fromResult, actualUserGetDtoResult.getBirthday());
        assertTrue(actualUserGetDtoResult.isActivated());
        assertEquals("foo", actualUserGetDtoResult.getCity());
        assertEquals("foo", actualUserGetDtoResult.getEmail());
        assertEquals("foo", actualUserGetDtoResult.getStreet());
        assertEquals("foo", actualUserGetDtoResult.getUsername());
        assertEquals("foo", actualUserGetDtoResult.getFirstName());
        assertEquals("foo", actualUserGetDtoResult.getCountry());
        assertEquals(1, actualUserGetDtoResult.getHouseNo());
        assertEquals("foo", actualUserGetDtoResult.getLastName());
        verify(user).getBirthday();
        verify(user).getCity();
        verify(user).getCountry();
        verify(user).getEmail();
        verify(user).getFirstName();
        verify(user).getHouseNo();
        verify(user).getLastName();
        verify(user).getStreet();
        verify(user).getUsername();
        verify(user).isActivated();
    }


    @Test
    public void testUserGetDtoNull() {
        assertNull(this.mapStructMapperImpl.userGetDto(null));
    }


    @Test
    public void testUserCrucialInfosDTO() {
        User user = new User();
        user.setId(123L);
        user.setEmail("foo");
        user.setActivated(false);
        user.setUsername("foo");
        user.setRole(Role.USER);
        UserCrucialInfosDTO actualUserCrucialInfosDTOResult = this.mapStructMapperImpl.userCrucialInfosDTO(user);
        assertEquals("foo", actualUserCrucialInfosDTOResult.getEmail());
        assertEquals(false, actualUserCrucialInfosDTOResult.isActivated());
        assertEquals("foo", actualUserCrucialInfosDTOResult.getUsername());
        assertEquals(Role.USER, actualUserCrucialInfosDTOResult.getRole());
    }


    @Test
    public void testUserCrucialInfosDTONull() {
        assertNull(this.mapStructMapperImpl.userCrucialInfosDTO(null));
    }


    @Test
    public void testAdminRegistrationNull() {
        assertNull(this.mapStructMapperImpl.adminRegistration(null));
    }

    @Test
    public void testAdminRegistration() {
        AdminRegistrationDTO adminRegistrationDTO = mock(AdminRegistrationDTO.class);
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        when(adminRegistrationDTO.getEdited()).thenReturn(ofResult);
        LocalDateTime ofResult1 = LocalDateTime.of(1, 1, 1, 1, 1);
        when(adminRegistrationDTO.getCreated()).thenReturn(ofResult1);
        when(adminRegistrationDTO.getRole()).thenReturn(Role.ADMIN);
        when(adminRegistrationDTO.getHouseNo()).thenReturn(1);
        when(adminRegistrationDTO.getStreet()).thenReturn("foo");
        when(adminRegistrationDTO.getCity()).thenReturn("foo");
        when(adminRegistrationDTO.getCountry()).thenReturn("foo");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        when(adminRegistrationDTO.getBirthday()).thenReturn(fromResult);
        when(adminRegistrationDTO.getLastName()).thenReturn("foo");
        when(adminRegistrationDTO.getFirstName()).thenReturn("foo");
        when(adminRegistrationDTO.isActivated()).thenReturn(true);
        when(adminRegistrationDTO.getPassword()).thenReturn("foo");
        when(adminRegistrationDTO.getUsername()).thenReturn("foo");
        when(adminRegistrationDTO.getEmail()).thenReturn("foo");
        when(adminRegistrationDTO.getId()).thenReturn(1L);
        User actualAdminRegistrationResult = this.mapStructMapperImpl.adminRegistration(adminRegistrationDTO);
        assertSame(fromResult, actualAdminRegistrationResult.getBirthday());
        assertTrue(actualAdminRegistrationResult.isActivated());
        assertEquals("foo", actualAdminRegistrationResult.getCity());
        assertSame(ofResult1, actualAdminRegistrationResult.getCreated());
        assertEquals(1, actualAdminRegistrationResult.getHouseNo().intValue());
        assertEquals("foo", actualAdminRegistrationResult.getUsername());
        assertEquals(1L, actualAdminRegistrationResult.getId());
        assertSame(ofResult, actualAdminRegistrationResult.getEdited());
        assertEquals("foo", actualAdminRegistrationResult.getLastName());
        assertEquals("foo", actualAdminRegistrationResult.getPassword());
        assertEquals("foo", actualAdminRegistrationResult.getCountry());
        assertEquals("foo", actualAdminRegistrationResult.getEmail());
        assertEquals(Role.ADMIN, actualAdminRegistrationResult.getRole());
        assertEquals("foo", actualAdminRegistrationResult.getFirstName());
        assertEquals("foo", actualAdminRegistrationResult.getStreet());
        verify(adminRegistrationDTO).getBirthday();
        verify(adminRegistrationDTO).getCity();
        verify(adminRegistrationDTO).getCountry();
        verify(adminRegistrationDTO).getCreated();
        verify(adminRegistrationDTO).getEdited();
        verify(adminRegistrationDTO).getEmail();
        verify(adminRegistrationDTO).getFirstName();
        verify(adminRegistrationDTO).getHouseNo();
        verify(adminRegistrationDTO).getId();
        verify(adminRegistrationDTO).getLastName();
        verify(adminRegistrationDTO).getPassword();
        verify(adminRegistrationDTO).getRole();
        verify(adminRegistrationDTO).getStreet();
        verify(adminRegistrationDTO).getUsername();
        verify(adminRegistrationDTO).isActivated();
    }

    @Test
    public void testUserRegistrationNull() {
        assertNull(this.mapStructMapperImpl.userRegistration(null));
    }

    @Test
    public void testUserRegistration() {
        UserRegistrationDTO userRegistrationDTO = mock(UserRegistrationDTO.class);
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        when(userRegistrationDTO.getEdited()).thenReturn(ofResult);
        LocalDateTime ofResult1 = LocalDateTime.of(1, 1, 1, 1, 1);
        when(userRegistrationDTO.getCreated()).thenReturn(ofResult1);
        when(userRegistrationDTO.getRole()).thenReturn(Role.USER);
        when(userRegistrationDTO.getHouseNo()).thenReturn(1);
        when(userRegistrationDTO.getStreet()).thenReturn("foo");
        when(userRegistrationDTO.getCity()).thenReturn("foo");
        when(userRegistrationDTO.getCountry()).thenReturn("foo");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        when(userRegistrationDTO.getBirthday()).thenReturn(fromResult);
        when(userRegistrationDTO.getLastName()).thenReturn("foo");
        when(userRegistrationDTO.getFirstName()).thenReturn("foo");
        when(userRegistrationDTO.isActivated()).thenReturn(false);
        when(userRegistrationDTO.getPassword()).thenReturn("foo");
        when(userRegistrationDTO.getUsername()).thenReturn("foo");
        when(userRegistrationDTO.getEmail()).thenReturn("foo");
        when(userRegistrationDTO.getId()).thenReturn(1L);
        User actualUserRegistrationResult = this.mapStructMapperImpl.userRegistration(userRegistrationDTO);
        assertSame(fromResult, actualUserRegistrationResult.getBirthday());
        assertFalse(actualUserRegistrationResult.isActivated());
        assertEquals("foo", actualUserRegistrationResult.getCity());
        assertSame(ofResult1, actualUserRegistrationResult.getCreated());
        assertEquals(1, actualUserRegistrationResult.getHouseNo().intValue());
        assertEquals("foo", actualUserRegistrationResult.getUsername());
        assertEquals(1L, actualUserRegistrationResult.getId());
        assertSame(ofResult, actualUserRegistrationResult.getEdited());
        assertEquals("foo", actualUserRegistrationResult.getLastName());
        assertEquals("foo", actualUserRegistrationResult.getPassword());
        assertEquals("foo", actualUserRegistrationResult.getCountry());
        assertEquals("foo", actualUserRegistrationResult.getEmail());
        assertEquals(Role.USER, actualUserRegistrationResult.getRole());
        assertEquals("foo", actualUserRegistrationResult.getFirstName());
        assertEquals("foo", actualUserRegistrationResult.getStreet());
        verify(userRegistrationDTO).getBirthday();
        verify(userRegistrationDTO).getCity();
        verify(userRegistrationDTO).getCountry();
        verify(userRegistrationDTO).getCreated();
        verify(userRegistrationDTO).getEdited();
        verify(userRegistrationDTO).getEmail();
        verify(userRegistrationDTO).getFirstName();
        verify(userRegistrationDTO).getHouseNo();
        verify(userRegistrationDTO).getId();
        verify(userRegistrationDTO).getLastName();
        verify(userRegistrationDTO).getPassword();
        verify(userRegistrationDTO).getRole();
        verify(userRegistrationDTO).getStreet();
        verify(userRegistrationDTO).getUsername();
        verify(userRegistrationDTO).isActivated();
    }

    @Test
    public void testUpdateUserNull() {
        assertNull(this.mapStructMapperImpl.updateUser(null));
    }

    @Test
    public void testUpdateUser() {
        UpdateUserDTO updateUserDTO = mock(UpdateUserDTO.class);
        LocalDateTime ofResult = LocalDateTime.of(1, 1, 1, 1, 1);
        when(updateUserDTO.getEdited()).thenReturn(ofResult);
        when(updateUserDTO.getHouseNo()).thenReturn(1);
        when(updateUserDTO.getStreet()).thenReturn("foo");
        when(updateUserDTO.getCity()).thenReturn("foo");
        when(updateUserDTO.getCountry()).thenReturn("foo");
        when(updateUserDTO.getLastName()).thenReturn("foo");
        when(updateUserDTO.getFirstName()).thenReturn("foo");
        when(updateUserDTO.getEmail()).thenReturn("foo");
        when(updateUserDTO.getId()).thenReturn(1L);
        User actualUpdateUserResult = this.mapStructMapperImpl.updateUser(updateUserDTO);
        assertEquals("foo", actualUpdateUserResult.getStreet());
        assertEquals("foo", actualUpdateUserResult.getLastName());
        assertEquals(1L, actualUpdateUserResult.getId());
        assertEquals(1, actualUpdateUserResult.getHouseNo().intValue());
        assertEquals("foo", actualUpdateUserResult.getFirstName());
        assertEquals("foo", actualUpdateUserResult.getEmail());
        assertSame(ofResult, actualUpdateUserResult.getEdited());
        assertEquals("foo", actualUpdateUserResult.getCity());
        assertEquals("foo", actualUpdateUserResult.getCountry());
        verify(updateUserDTO).getCity();
        verify(updateUserDTO).getCountry();
        verify(updateUserDTO).getEdited();
        verify(updateUserDTO).getEmail();
        verify(updateUserDTO).getFirstName();
        verify(updateUserDTO).getHouseNo();
        verify(updateUserDTO, times(2)).getId();
        verify(updateUserDTO).getLastName();
        verify(updateUserDTO).getStreet();
    }
}

