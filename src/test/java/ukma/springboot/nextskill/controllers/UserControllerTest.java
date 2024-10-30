package ukma.springboot.nextskill.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ukma.springboot.nextskill.dto.UserDto;
import ukma.springboot.nextskill.interfaces.IUserService;
import ukma.springboot.nextskill.model.mappers.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private IUserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        // Створюємо тестового користувача
        UserDto testUser = new UserDto();
        testUser.setName("Test User");
        testUser.setDescription("Test Description");

        // Мокаємо сервіс
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(UserMapper.toUser(testUser)));

        // Викликаємо метод
        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        // Перевіряємо результат
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getName()).isEqualTo("Test User");

        // Перевіряємо, що метод сервісу викликаний один раз
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void addUser_shouldCreateUser() {
        // Створюємо тестового користувача
        UserDto newUser = new UserDto();
        newUser.setName("New User");
        newUser.setDescription("New Description");

        // Мокаємо створення користувача
        when(userService.createUser(any())).thenReturn(UserMapper.toUser(newUser));

        // Викликаємо метод
        ResponseEntity<UserDto> response = userController.addUser(newUser);

        // Перевіряємо результат
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("New User");

        // Перевіряємо, що метод сервісу викликаний один раз
        verify(userService, times(1)).createUser(any());
    }
}
