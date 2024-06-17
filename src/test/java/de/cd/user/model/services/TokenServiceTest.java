package de.cd.user.model.services;

import de.cd.user.model.TokenRepository;
import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.Role;
import de.cd.user.model.entities.Token;
import de.cd.user.model.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TokenService.class})
@ExtendWith(SpringExtension.class)
public class TokenServiceTest {
    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private TokenService tokenService;

    @MockBean
    private UserRepository userRepository;

    private User user;

    private Token token;

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
        user.setActivated(true);
        user.setPasswordHash("Password Hash");
        user.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setUsername("AlexTest");
        user.setCountry("Country");
        user.setEdited(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setCity("Oxford");
        user.setId((Long) 1L);
        user.setStreet("Street");

        this.token = new Token();
        token.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setId(1L);
        token.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setToken("ABC123");
        token.setUser(user);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testSaveToken() {
        when(this.tokenRepository.save(any())).thenReturn(token);
        this.tokenService.saveToken(new User(), "ABC123");
        verify(this.tokenRepository).save(any());
    }

    @Test
    public void testGetToken() {
        Optional<Token> ofResult = Optional.of(token);
        when(this.tokenRepository.findByToken(anyString())).thenReturn(ofResult);
        Optional<Token> actualToken = this.tokenService.getToken("ABC123");
        assertSame(ofResult, actualToken);
        assertTrue(actualToken.isPresent());
        verify(this.tokenRepository).findByToken(anyString());
    }

    @Test
    public void testSetConfirmedAt() {
        when(this.tokenRepository.updateConfirmedAt(anyString(), any())).thenReturn(1);
        assertEquals(1, this.tokenService.setConfirmedAt("ABC123"));
        verify(this.tokenRepository).updateConfirmedAt(anyString(), any());
    }

    @Test
    public void testGetTokenByUserId() {
        ArrayList<Token> tokenList = new ArrayList<Token>();
        when(this.tokenRepository.findByUserId(any())).thenReturn(tokenList);
        List<Token> actualTokenByUserId = this.tokenService.getTokenByUserId(1L);
        assertSame(tokenList, actualTokenByUserId);
        assertTrue(actualTokenByUserId.isEmpty());
        verify(this.tokenRepository).findByUserId(any());
    }

    @Test
    public void testGetTokenById() {
        Optional<Token> ofResult = Optional.of(token);
        when(this.tokenRepository.findById(anyLong())).thenReturn(ofResult);
        Optional<Token> actualTokenById = this.tokenService.getTokenById(1L);
        assertSame(ofResult, actualTokenById);
        assertTrue(actualTokenById.isPresent());
        verify(this.tokenRepository).findById(anyLong());
    }
}

