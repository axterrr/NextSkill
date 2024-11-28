package ukma.springboot.nextskill.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JWTUtilityTests {

    private static final String SECRET_KEY = "test_secret_key";
    private static final long EXPIRATION_TIME = 3600000L;
    private static final String TEST_USERNAME = "test_user";
    private static final List<SimpleGrantedAuthority> TEST_AUTHORITIES =
            List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));

    @InjectMocks
    private JWTUtility jwtUtility;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtility, "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(jwtUtility, "expirationTime", EXPIRATION_TIME);
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtility.getToken(TEST_USERNAME, TEST_AUTHORITIES);
        assertNotNull(token, "Generated token should not be null");
    }

    @Test
    void testVerifyToken() {
        String token = jwtUtility.getToken(TEST_USERNAME, TEST_AUTHORITIES);

        DecodedJWT decodedJWT = jwtUtility.verifyToken(token);

        assertNotNull(decodedJWT, "Decoded JWT should not be null");
        assertEquals(TEST_USERNAME, decodedJWT.getSubject(), "Username should match the subject in the token");
        assertEquals(2, decodedJWT.getClaim("roles").asList(String.class).size(), "Token should contain correct roles");
        assertTrue(decodedJWT.getExpiresAt().after(new Date()), "Token expiration should be in the future");
    }

    @Test
    void testVerifyTokenWithInvalidToken() {
        String invalidToken = "invalid.token.string";

        assertThrows(JWTVerificationException.class, () -> jwtUtility.verifyToken(invalidToken),
                "Verifying an invalid token should throw JWTVerificationException");
    }
}
