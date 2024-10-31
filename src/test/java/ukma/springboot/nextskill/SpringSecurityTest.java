package ukma.springboot.nextskill;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ukma.springboot.nextskill.entities.RoleEntity;
import ukma.springboot.nextskill.entities.UserEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminAccess() throws Exception {
        mockMvc.perform(get("/api/user/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAuthMe() throws Exception {
        String mockUserUsername = RandomStringUtils.randomAscii(20);
        String mockUserEmail = RandomStringUtils.randomAscii(8) + "@random.mail.com";
        String mockUserDescription = RandomStringUtils.randomAscii(30);
        UUID randomUuid = UUID.randomUUID();

        RoleEntity adminRole = new RoleEntity();
        adminRole.setId(2);
        adminRole.setTitle("ADMIN");

        RoleEntity studentRole = new RoleEntity();
        studentRole.setId(0);
        studentRole.setTitle("STUDENT");

        HashSet<RoleEntity> roles = HashSet.newHashSet(2);
        roles.add(adminRole);
        roles.add(studentRole);

        UserEntity mockUserEntity = getMockUserEntity(mockUserUsername, randomUuid, mockUserEmail, mockUserDescription, roles);

        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUserEntity, "password", mockUserEntity.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MvcResult result = mockMvc.perform(get("/auth/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<>() {});

        assertEquals(randomUuid.toString(), responseMap.get("uuid"));
        assertEquals(mockUserUsername, responseMap.get("username"));
        assertEquals(mockUserEmail, responseMap.get("email"));
        assertEquals(mockUserDescription, responseMap.get("description"));

        List<Map<String, Object>> rolesInResponse = (List<Map<String, Object>>) responseMap.get("roles");

        assertEquals(2, rolesInResponse.size());

        assertTrue(rolesInResponse.stream().anyMatch(role ->
                role.get("id").equals(adminRole.getId()) && role.get("title").equals(adminRole.getTitle())));

        assertTrue(rolesInResponse.stream().anyMatch(role ->
                role.get("id").equals(studentRole.getId()) && role.get("title").equals(studentRole.getTitle())));
    }

    private static UserEntity getMockUserEntity(String mockUserUsername, UUID randomUuid,
                                                String mockUserEmail, String mockUserDescription,
                                                HashSet<RoleEntity> roles) {
        UserEntity mockUserEntity = Mockito.mock(UserEntity.class);

        Mockito.when(mockUserEntity.getUsername()).thenReturn(mockUserUsername);
        Mockito.when(mockUserEntity.getUuid()).thenReturn(randomUuid);
        Mockito.when(mockUserEntity.getEmail()).thenReturn(mockUserEmail);
        Mockito.when(mockUserEntity.getDescription()).thenReturn(mockUserDescription);

        Mockito.when(mockUserEntity.getRoles()).thenReturn(roles);
        return mockUserEntity;
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testAdminAccessDenied() throws Exception {
        mockMvc.perform(get("/api/user/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUnauthorized() throws Exception {
        mockMvc.perform(get("/api/course/all"))
                .andExpect(status().isUnauthorized());
    }
}
