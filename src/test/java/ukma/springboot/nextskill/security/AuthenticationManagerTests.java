package ukma.springboot.nextskill.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import ukma.springboot.nextskill.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.security.managers.AuthenticationManagerImpl;
import ukma.springboot.nextskill.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationManagerTests {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationManagerImpl authenticationManager;

    private String username = "test_user";
    private String password = "password123";
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPasswordHash("$2a$10$D2fUJfX8jtNz5QKYX.JU6OyzkzOZ3dHkL9ehPqXG.dmrQtXsC7X5W");
        userEntity.setRole(UserRole.STUDENT);
    }

    @Test
    void testAuthenticate_Success() {
        when(userService.getUserByUsername(username)).thenReturn(userEntity);
        when(passwordEncoder.matches(password, userEntity.getPasswordHash())).thenReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication result = authenticationManager.authenticate(authentication);

        assertNotNull(result);
        assertEquals(username, result.getPrincipal());
        assertTrue(result.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT")));
        verify(userService, times(1)).getUserByUsername(username);
        verify(passwordEncoder, times(1)).matches(password, userEntity.getPasswordHash());
    }

    @Test
    void testAuthenticate_IncorrectUsername() {
        when(userService.getUserByUsername(username)).thenThrow(ResourceNotFoundException.class);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        assertThrows(BadCredentialsException.class, () -> authenticationManager.authenticate(authentication));
        verify(userService, times(1)).getUserByUsername(username);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void testAuthenticate_IncorrectPassword() {
        when(userService.getUserByUsername(username)).thenReturn(userEntity);
        when(passwordEncoder.matches(password, userEntity.getPasswordHash())).thenReturn(false);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        assertThrows(BadCredentialsException.class, () -> authenticationManager.authenticate(authentication));
        verify(userService, times(1)).getUserByUsername(username);
        verify(passwordEncoder, times(1)).matches(password, userEntity.getPasswordHash());
    }
}
