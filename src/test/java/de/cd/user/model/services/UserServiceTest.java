package de.cd.user.model.services;

import de.cd.user.inbound.DTO.UserCrucialInfosDTO;
import de.cd.user.model.TokenRepository;
import de.cd.user.model.UserRatingClient;
import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.Role;
import de.cd.user.model.entities.Token;
import de.cd.user.model.entities.User;
import de.cd.user.model.exceptions.AlreadyExistsException;
import de.cd.user.model.exceptions.ExpiredException;
import de.cd.user.model.exceptions.ResourceNotFoundException;
import de.cd.user.model.util.MapStructMapperImpl;
import de.cd.user.outbound.MessageProducerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {EmailService.class, TokenService.class, UserService.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "ratingForMod=1",
})
public class UserServiceTest {
    @Value("${ratingForMod}")
    int userRatingforMod;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private EmailService emailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    UserRatingClient userRatingClient;

    @MockBean
    private MessageProducerImpl messageProducerImpl;

    @MockBean
    private MapStructMapperImpl mapStructMapper;

    @Autowired
    private UserService userService;

    private User user;

    private Token token;

    private UserCrucialInfosDTO userCrucialInfosDTO;

    @BeforeEach
    void setUp() {
        this.user = new User();
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
        user.setActivated(false);
        user.setPasswordHash("Password Hash");
        user.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setUsername("AlexTest");
        user.setCountry("Country");
        user.setEdited(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setCity("Berlin");
        user.setId((Long) 1L);
        user.setStreet("Street");

        this.token = new Token();
        token.setConfirmedAt(null);
        token.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setId(1L);
        token.setExpiresAt(LocalDateTime.of(2030, 1, 1, 1, 1));
        token.setToken("ABC123");
        token.setUser(user);

        this.userCrucialInfosDTO = new UserCrucialInfosDTO();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testDeleteAllUsers() {
        doNothing().when(this.userRepository).deleteAll();
        this.userService.deleteAllUsers();
        verify(this.userRepository).deleteAll();
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testDeleteUserById() {
        doNothing().when(this.userRepository).deleteById(anyLong());
        this.userService.deleteUserById(1L);
        verify(this.userRepository).deleteById(anyLong());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testGetAllUsers() {
        when(this.userRepository.findAll()).thenThrow(new ExpiredException("An error occurred"));
        assertThrows(ExpiredException.class, () -> this.userService.getAllUsers());
        verify(this.userRepository).findAll();
    }

    @Test
    public void testGetUserById() {
        when(this.userRepository.findById(anyLong())).thenReturn(user);
        assertSame(user, this.userService.getUserById(1L));
        verify(this.userRepository).findById(anyLong());
    }

    @Test
    public void testGetUserByUsername() {
        when(this.userRepository.findByUserName(anyString())).thenReturn(user);
        assertSame(user, this.userService.getUserByUsername("AlexTest"));
        verify(this.userRepository).findByUserName(anyString());
    }

    @Test
    public void testSave() {
        when(this.userRepository.save(any())).thenReturn(user);
        this.userService.save(new User());
        verify(this.userRepository).save(any());
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void testGetUserByEmail() {
        when(this.userRepository.findByEmail(anyString())).thenReturn(user);
        assertSame(user, this.userService.getUserByEmail("Alex.Test@example.org"));
        verify(this.userRepository).findByEmail(anyString());
    }

    @Test
    public void testRegisterUsernameExists() {
        when(this.userRepository.findByUserName(anyString())).thenReturn(user);
        user = userRepository.findByUserName(anyString());
        User finalUser = user;
        assertThrows(AlreadyExistsException.class, () -> this.userService.register(finalUser));
        verify(this.userRepository, times(2)).findByUserName(anyString());
    }

    @Test
    public void testRegisterEmailExists() {
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByEmail(anyString())).thenReturn(ofResult.get());
        user = userRepository.findByEmail(anyString());
        User finalUser = user;
        assertThrows(AlreadyExistsException.class, () -> this.userService.register(finalUser));
        verify(this.userRepository, times(2)).findByEmail(anyString());
    }

    @Test
    public void testRegister() {
        when(this.userRepository.findByUserName(anyString())).thenReturn(null);
        //when(userRepository.findByUserName(anyString()).getId()).thenReturn(1L);
        this.userCrucialInfosDTO.setUsername("ingo");
        User finalUser = user;
        assertSame(this.userService.register(user), finalUser);
    }


    @Test
    public void testLogin() {
        //when(this.userService.login(anyString(),anyString())).thenReturn(user);
        //when(this.authenticationManager.authenticate(any())).thenReturn(true)
        assertNull(this.userService.login("AlexTest", "Test123"));
        assertTrue(this.userService.getAllUsers().isEmpty());
    }

    @Test
    public void failLogin() {
        assertThrows(ResourceNotFoundException.class, () -> this.userService.login(anyString(), anyString()));
    }

    @Test
    public void testConfirmTokenExists() {
        Token token2 = new Token();
        token2.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token2.setId(1L);
        token2.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token2.setToken("ABC123");
        token2.setUser(user);
        Optional<Token> ofResult = Optional.of(token2);
        when(this.tokenService.getToken(anyString())).thenReturn(ofResult);
        assertThrows(AlreadyExistsException.class, () -> this.userService.confirmToken("ABC123"));
        verify(this.tokenService).getToken(anyString());
    }

    @Test
    public void testConfirmTokenExpired() {
        this.token.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<Token> ofResult = Optional.of(token);
        when(this.tokenService.getToken(anyString())).thenReturn(ofResult);
        assertThrows(ExpiredException.class, () -> this.userService.confirmToken("ABC123"));
        verify(this.tokenService).getToken(anyString());
    }

    @Test
    public void testConfirmTokenNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> this.userService.confirmToken(anyString()));
    }

    @Test
    public void testConfirmToken() {
        when(this.tokenService.getToken(anyString())).thenReturn(Optional.of(token));
        userService.confirmToken("ABC123");
        assertEquals(userService.confirmToken("ABC123"), "confirmed");
    }

    @Test
    public void testChangePassword() throws AuthenticationException {
        when(this.userRepository.findByUserName(anyString())).thenReturn(user);
        when(this.authenticationManager.authenticate(any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        assertSame(user, this.userService.changePassword("AlexTest", "Test123", "Password123", "Password123"));
        verify(this.userRepository, times(2)).findByUserName(anyString());
        verify(this.authenticationManager).authenticate(any());
    }

    @Test
    public void testChangeAuthorityException() {
        when(this.userRepository.findById(1)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> this.userService.changeAuthority(1));
    }

    @Test
    public void testUpdateUser(){
        when(userRepository.findById(anyLong())).thenReturn(user);
        when(mapStructMapper.updateUser(any())).thenReturn(user);
        User user = userRepository.findById(anyLong());
        User user1 = mapStructMapper.updateUser(any());
        verify(userRepository).findById(anyLong());
        verify(mapStructMapper).updateUser(any());
    }

}

