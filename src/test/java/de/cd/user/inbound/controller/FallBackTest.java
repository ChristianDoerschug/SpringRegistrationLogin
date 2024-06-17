package de.cd.user.inbound.controller;

import de.cd.user.model.UserRepository;
import de.cd.user.model.services.UserService;
import de.cd.user.inbound.security.JwtProvider;
import de.cd.user.model.services.EmailService;
import de.cd.user.model.services.TokenService;
import de.cd.user.model.util.MapStructMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class FallBackTest {

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

    @Test
    void testFallBack() throws Exception {
        this.mvc.perform(get("/fallback"))
                .andExpect(status().isOk())
                .andExpect(content().string("Leider ist der Service gerade nicht erreichbar"));
    }

}