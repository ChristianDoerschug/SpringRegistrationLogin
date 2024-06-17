package de.cd.user.inbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cd.user.inbound.DTO.*;
import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.Role;
import de.cd.user.model.entities.Token;
import de.cd.user.model.entities.User;
import de.cd.user.model.services.UserService;
import de.cd.user.inbound.security.JwtProvider;
import de.cd.user.model.services.EmailService;
import de.cd.user.model.services.TokenService;
import de.cd.user.model.util.MapStructMapperImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private MapStructMapperImpl mapStructMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmailService emailService;

    private User user;

    private User user1;

    private Token token;

    private List<User> users;

    private List<Token> tokens;

    private UserGetDto userGetDto;

    private LoginDTO loginDTO;

    private AdminRegistrationDTO adminRegistrationDTO;

    private UserController userController;

    private ObjectMapper objectMapper;

    private UserRegistrationDTO userRegistrationDTO;

    private ForgotPasswordDTO forgotPasswordDTO;

    private final String AUTH_HEADER = "Bearer ANY-JWT-STRING";

    private PasswordDTO passwordDTO;

    @BeforeEach
    void setUp() throws ParseException {
        userController = new UserController(userRepository, mapStructMapper, userService, jwtProvider, passwordEncoder, tokenService, emailService);
        String date = "1990-01-01";
        Date date1 = new SimpleDateFormat("yyyy-MM-DD").parse(date);
        this.users = new ArrayList<>();
        this.user = new User();
        user.setLastName("Test");
        user.setTokens(new ArrayList<Token>());
        user.setEmail("Alex.Test@example.org");
        user.setPassword("Password123");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setBirthday(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        user.setRole(Role.USER);
        user.setId(1L);
        user.setFirstName("Alex");
        user.setHouseNo(1);
        user.setActivated(true);
        user.setPasswordHash("Password Hash");
        user.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setUsername("AlexTest");
        user.setCountry("Country");
        user.setEdited(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setCity("Berlin");
        user.setId((Long) 1L);
        user.setStreet("Street");
        this.user1 = new User();
        user1.setLastName("Test");
        user1.setTokens(new ArrayList<Token>());
        user1.setEmail("john.Test@example.org");
        user1.setPassword("Password123");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirthday(Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant()));
        user1.setRole(Role.USER);
        user1.setId(1234L);
        user1.setFirstName("John");
        user1.setHouseNo(1);
        user1.setActivated(true);
        user1.setPasswordHash("Password Hash");
        user1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setUsername("johnTest");
        user1.setCountry("Country");
        user1.setEdited(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setCity("Berlin");
        user1.setId((Long) 1234L);
        user1.setStreet("Street");

        this.userGetDto = new UserGetDto();
        userGetDto.setActivated(true);
        userGetDto.setEmail("Alex.Test@example.org");
        userGetDto.setUsername("AlexTest");

        this.loginDTO = new LoginDTO();
        //loginDTO.setEmail("Alex.Test@example.org");
        loginDTO.setUsername("Alexode");
        loginDTO.setPassword("Password123");

        this.tokens = new ArrayList<>();
        this.token = new Token();
        token.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setId(1L);
        token.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setToken("ABC123");
        token.setUser(user);

        this.userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setLastName("Test");
        userRegistrationDTO.setEmail("Alex.Test@example.org");
        userRegistrationDTO.setPassword("Password123");
        userRegistrationDTO.setBirthday(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        //userRegistrationDTO.setRole(Role.USER);
        userRegistrationDTO.setId(1L);
        userRegistrationDTO.setFirstName("Alex");
        userRegistrationDTO.setHouseNo(1);
        //userRegistrationDTO.setActivated(true);
        //userRegistrationDTO.setPasswordHash("Password Hash");
        //userRegistrationDTO.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        userRegistrationDTO.setUsername("AlexTest");
        userRegistrationDTO.setCountry("Country");
        //user.setEdited(LocalDateTime.of(1, 1, 1, 1, 1));
        userRegistrationDTO.setCity("Berlin");
        userRegistrationDTO.setId(1L);
        userRegistrationDTO.setStreet("Street");

        this.passwordDTO = new PasswordDTO();
        passwordDTO.setPassword("Password123");
        passwordDTO.setNewPassword("123Password");
        passwordDTO.setConfirmPassword("123Password");

        this.forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setEmail("Alex.Test@example.org");



        this.objectMapper = new ObjectMapper();

        users.add(user);
        tokens.add(token);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("AlexTest")
                .password("***")
                .authorities(Role.USER.getAuthority())
                .build();

        given(jwtProvider.validateToken(any(String.class))).willReturn(true);
        given(jwtProvider.getUsername(any(String.class))).willReturn("AlexTest");
        given(jwtProvider.resolveToken(any(HttpServletRequest.class))).willReturn(AUTH_HEADER.substring(7));
        given(jwtProvider.getAuthentication(any(String.class))).willReturn(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testGetByUserName() throws Exception {
        given(userService.getUserByUsername("AlexTest")).willReturn(user);
        given(mapStructMapper.userGetDto(user)).willReturn(userGetDto);
        this.mvc.perform(get("/user/userinfo", this.AUTH_HEADER)
                .header("Authorization", this.AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    void testRegister() throws Exception {
        given(this.userService.register(any())).willReturn(user);
        given(userService.getUserByUsername(anyString())).willReturn(user);
        given(this.mapStructMapper.userRegistration(userRegistrationDTO)).willReturn(user);
        this.mvc.perform(post("/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                //.accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegistrationDTO)))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(this.userService, times(1)).register(any());
    }


    @Test
    public void testLogin() throws Exception {
        given(userService.getUserByEmail(anyString())).willReturn(user);
        given(userService.login(anyString(), anyString())).willReturn(user);
        this.mvc.perform(post("/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void testChangePassword() throws Exception {
        UserController userController = new UserController(null, mapStructMapper, userService, jwtProvider, passwordEncoder, tokenService, emailService);
        given(userService.changePassword(anyString(), anyString(), anyString(), anyString())).willReturn(user);
        this.mvc.perform(put("/user/changePassword")
                .header("Authorization", this.AUTH_HEADER)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passwordDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testActivateUser() throws Exception {
        given(userService.confirmToken(anyString())).willReturn("Success");
        given(tokenService.getToken(anyString())).willReturn(Optional.of(token));
        this.mvc.perform(get("/user/activate")
                .param("token", token.getToken()))
                .andExpect(status().isOk())
                .andDo(print());
        verify(userService).confirmToken(anyString());
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUserById(1L);
        when(userRepository.findByUserName(anyString())).thenReturn(user);
        this.mvc.perform(delete("/user")
                .header("Authorization", this.AUTH_HEADER))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUserById(1L);
    }

    @Test
    public void testUpdateUser() throws Exception {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setLastName("Test");
        updateUserDTO.setEmail("Alex.Test@example.org");
        updateUserDTO.setCountry("Country");
        updateUserDTO.setId(1L);
        updateUserDTO.setCity("Berlin");
        updateUserDTO.setFirstName("Alex");
        updateUserDTO.setHouseNo(1);
        updateUserDTO.setStreet("Street");
        when(mapStructMapper.updateUser(any())).thenReturn(user);
        this.mvc.perform(put("/user")
                .header("Authorization", this.AUTH_HEADER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testForgotPassword() throws Exception {
        given(userService.getUserByEmail(anyString())).willReturn(user);
        given(userRepository.save(any())).willReturn(user);
        this.mvc.perform(post("/user/forgotPassword")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(forgotPasswordDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

