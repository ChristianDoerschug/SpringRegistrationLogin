package de.cd.user.inbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.Role;
import de.cd.user.model.entities.Token;
import de.cd.user.model.entities.User;
import de.cd.user.model.services.UserService;
import de.cd.user.inbound.DTO.AdminRegistrationDTO;
import de.cd.user.inbound.DTO.UserCrucialInfosDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AdminControllerTest {

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

    private ObjectMapper objectMapper;

    private User user;

    private User user1;

    private Token token;

    private List<User> users;

    private List<Token> tokens;

    private UserCrucialInfosDTO userCrucialInfosDTO;

    private AdminRegistrationDTO adminRegistrationDTO;

    private final String AUTH_HEADER = "Bearer ANY-JWT-STRING";

    @BeforeEach
    void setUp() throws ParseException {
        this.objectMapper = new ObjectMapper();
        this.users = new ArrayList<>();
        this.user = new User();
        user.setLastName("Test");
        user.setTokens(new ArrayList<Token>());
        user.setEmail("Alex.Test@fh-muenster.de");
        user.setPassword("!Password123");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date date1 = new Date(1970, 1, 1);
        user.setBirthday(date1);
        user.setRole(Role.ADMIN);
        user.setId(1);
        user.setFirstName("Alex");
        user.setHouseNo(1);
        user.setActivated(true);
        user.setPasswordHash("Password Hash");
        user.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setUsername("AlexTest");
        user.setCountry("Country");
        user.setEdited(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setCity("Berlin");
        user.setStreet("Street");

        this.user1 = new User();
        user1.setLastName("Test");
        user1.setTokens(new ArrayList<Token>());
        user1.setEmail("Lea.Test@example.org");
        user1.setPassword("Test123");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setBirthday(date1);
        user1.setRole(Role.USER);
        user1.setId(2);
        user1.setFirstName("Lea");
        user1.setHouseNo(1);
        user1.setActivated(true);
        user1.setPasswordHash("Password Hash");
        user1.setCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setUsername("LeaTest");
        user1.setCountry("Country");
        user1.setEdited(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setCity("Berlin");
        user1.setStreet("Street");

        this.userCrucialInfosDTO = new UserCrucialInfosDTO();
        userCrucialInfosDTO.setUsername("AlexTest");
        userCrucialInfosDTO.setEmail("Alex.Test@fh-muenster.de");
        userCrucialInfosDTO.setActivated(true);
        userCrucialInfosDTO.setRole(Role.ADMIN);

        this.adminRegistrationDTO = new AdminRegistrationDTO();
        adminRegistrationDTO.setLastName("Test");
        adminRegistrationDTO.setEmail("Alex.Test@fh-muenster.de");
        adminRegistrationDTO.setPassword("!Password123");
        adminRegistrationDTO.setBirthday(date1);
        adminRegistrationDTO.setId(1);
        adminRegistrationDTO.setFirstName("Alex");
        adminRegistrationDTO.setHouseNo(1);
        adminRegistrationDTO.setUsername("AlexTest");
        adminRegistrationDTO.setCountry("Country");
        adminRegistrationDTO.setCity("Berlin");
        adminRegistrationDTO.setStreet("Street");

        this.tokens = new ArrayList<>();
        this.token = new Token();
        token.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setId(1L);
        token.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        token.setToken("ABC123");
        token.setUser(user);

        users.add(user);
        tokens.add(token);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("AlexTest")
                .password("***")
                .authorities(Role.ADMIN.getAuthority())
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
    public void testGetAllUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(users);
        this.mvc.perform(get("/admin/allUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(users.size())));
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        final String username = "AlexTest";
        given(userService.getUserByUsername(username)).willReturn(user);
        this.mvc.perform(get("/admin/{id}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andDo(print());
    }

    @Test
    public void testGetCrucialById() throws Exception {
        final String username = "AlexTest";
        AdminController adminController = new AdminController(mapStructMapper, userService, jwtProvider, passwordEncoder, tokenService);
        ResponseEntity<UserCrucialInfosDTO> actualCrucialById = adminController.getCrucialById("AlexTest");
        assertEquals(HttpStatus.OK, actualCrucialById.getStatusCode());
        given(userService.getUserByUsername("AlexTest")).willReturn(user);
        given(mapStructMapper.userCrucialInfosDTO(user)).willReturn(userCrucialInfosDTO);
        this.mvc.perform(get("/admin/{username}/crucial", "AlexTest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(userCrucialInfosDTO.getEmail())))
                .andExpect(jsonPath("$.username", is(userCrucialInfosDTO.getUsername())));
    }

    @Test
    public void testGetTokenByUserId() throws Exception {
        final Long userId = 1L;
        //System.out.println(tokens.get(0).getId());
        given(tokenService.getTokenByUserId(1)).willReturn(tokens);
        this.mvc.perform(get("/admin/{id}/token/UserId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(tokens.size())));
    }

    @Test
    void testRegister() throws Exception {
        given(this.userService.register(any())).willReturn(user);
        given(userService.getUserByUsername(anyString())).willReturn(user);
        given(this.mapStructMapper.adminRegistration(adminRegistrationDTO)).willReturn(user);
        this.mvc.perform(post("/admin/registration")
                .contentType(MediaType.APPLICATION_JSON)
                //.accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminRegistrationDTO)))
                //.content("{\"lastname\": \"Test\", \"email\": \"Alex.Test@fh-muenster.de\", \"password\": \"Test123\", \"birthday\": \"1990-01-01\",\"id\": \"1\", \"firstName\": \"Alex\", \"houseNo\": \"1\", \"username\": \"AlexTest\", \"country\": \"Country\", \"city\": \"Berlin\", \"street\": \"Street\"}"))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(this.userService, times(1)).register(any());
    }


    @Test
    public void testDeleteAllUsers() throws Exception {
        AdminController adminController = new AdminController(mapStructMapper, userService, jwtProvider, passwordEncoder, tokenService);
        adminController.deleteAllUsers("AlexTest");
        given(this.userService.deleteAllUsers()).willReturn("Success");
        this.mvc.perform(delete("/admin/deleteAllUsers")
                .header("Authorization", this.AUTH_HEADER))
                .andExpect(status().isOk());
        verify(userService, times(2)).deleteAllUsers();
        assertTrue(adminController.getAllUsers().isEmpty());
    }

    @Test
    public void testChangeAuthority() {
        AdminController adminController = new AdminController(mapStructMapper, userService, jwtProvider, passwordEncoder,
                tokenService);
        adminController.changeAuthority(1);
        verify(userService).changeAuthority(anyLong());
        assertTrue(adminController.getAllUsers().isEmpty());
    }
}

