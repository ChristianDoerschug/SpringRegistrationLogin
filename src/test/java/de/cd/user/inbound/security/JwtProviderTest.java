package de.cd.user.inbound.security;

import de.cd.user.model.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtProviderTest {

    private JwtProvider jwtProvider;


    @Test
    public void testValidateToken() {
        assertThrows(ResourceNotFoundException.class, () -> (new JwtProvider()).validateToken("ABC123"));
    }

    @Test
    public void testResolveToken() {
        JwtProvider jwtProvider = new JwtProvider();
        assertNull(jwtProvider.resolveToken(new MockHttpServletRequest()));
    }
}

