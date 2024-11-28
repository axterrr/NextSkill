package ukma.springboot.nextskill.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ukma.springboot.nextskill.models.entities.UserEntity;
import ukma.springboot.nextskill.models.enums.UserRole;
import ukma.springboot.nextskill.models.responses.UserResponse;
import ukma.springboot.nextskill.models.views.UserView;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.services.implementations.UserServiceImpl;
import ukma.springboot.nextskill.validation.UserValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID userId;
    private UserEntity userEntity;
    private UserView userView;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        userEntity = new UserEntity();
        userEntity.setUuid(userId);
        userEntity.setRole(UserRole.STUDENT);
        userEntity.setUsername("test_user");

        userView = new UserView();
        userView.setUuid(userId);
        userView.setRole(UserRole.STUDENT);
        userView.setUsername("test_user");

        userResponse = new UserResponse();
        userResponse.setUuid(userId);
        userResponse.setRole(UserRole.STUDENT);
        userResponse.setUsername("test_user");
    }

    @Test
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        List<UserResponse> result = userService.getAll();
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUuid());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        UserResponse result = userService.get(userId);
        assertEquals(userId, result.getUuid());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        UserResponse result = userService.create(userView);
        assertEquals(userId, result.getUuid());
        verify(userValidator, times(1)).validateForCreation(userView);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserResponse result = userService.update(userView);
        assertEquals(userId, result.getUuid());
        verify(userValidator, times(1)).validateForUpdate(userView);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testIsAdmin() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        userEntity.setRole(UserRole.ADMIN);

        boolean result = userService.isAdmin(userId);
        assertTrue(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAuthenticatedUser() {
        when(userRepository.findByUsername("test_user")).thenReturn(Optional.of(userEntity));
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("test_user", null));

        UserResponse result = userService.getAuthenticatedUser();
        assertEquals(userId, result.getUuid());
        verify(userRepository, times(1)).findByUsername("test_user");
    }
}
