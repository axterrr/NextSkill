//package ukma.springboot.nextskill.controllers;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import ukma.springboot.nextskill.dto.UserDto;
//import ukma.springboot.nextskill.model.mappers.UserMapper;
//import ukma.springboot.nextskill.services.UserService;
//
//import java.util.Collections;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.times;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//    @Autowired
//    private UserController userController;
//
//    @MockBean
//    private UserService userService;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    @WithMockUser(username = "test_user", roles = "ADMIN")
//    void getAllUsers_shouldReturnAllUsers() throws Exception {
//        UserDto testUser = new UserDto();
//        testUser.setUuid(UUID.randomUUID());
//        testUser.setUsername("test_user");
//        testUser.setName("Test");
//        testUser.setSurname("User");
//        testUser.setEmail("test@gmail.com");
//        testUser.setDescription("Test Description");
//
//        when(userService.getAllUsers()).thenReturn(Collections.singletonList(UserMapper.toUser(testUser)));
//
//        mockMvc.perform(get("/api/user/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].username").value("test_user"))
//                .andExpect(jsonPath("$[0].name").value("Test"))
//                .andExpect(jsonPath("$[0].surname").value("User"));
//
//        verify(userService, times(1)).getAllUsers();
//    }
//
//
//    @Test
//    void addUser_shouldCreateUser() {
//        UserDto testUser = new UserDto();
//        testUser.setUuid(UUID.randomUUID());
//        testUser.setUsername("test_user");
//        testUser.setName("Test");
//        testUser.setSurname("User");
//        testUser.setEmail("test@gmail.com");
//        testUser.setDescription("Test Description");
//
//        when(userService.createUser(any())).thenReturn(UserMapper.toUser(testUser));
//
//        ResponseEntity<UserDto> response = userController.addUser(testUser);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody().getUsername()).isEqualTo("test_user");
//        assertThat(response.getBody().getName()).isEqualTo("Test");
//        assertThat(response.getBody().getSurname()).isEqualTo("User");
//
//        verify(userService, times(1)).createUser(any());
//    }
//}
