package ukma.springboot.nextskill.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ukma.springboot.nextskill.dto.CourseDto;

import ukma.springboot.nextskill.dto.UserDto;
import ukma.springboot.nextskill.model.Course;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.model.mappers.CourseMapper;
import ukma.springboot.nextskill.model.mappers.UserMapper;
import ukma.springboot.nextskill.security.JwtAuthFilter;
import ukma.springboot.nextskill.security.JwtService;
import ukma.springboot.nextskill.services.CourseService;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    public void testGetAllCourses() throws Exception {
        when(courseService.getAllCourses()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/course/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetCourseById() throws Exception {
        UUID courseId = UUID.randomUUID();

        CourseDto courseDto = new CourseDto();
        courseDto.setUuid(courseId);
        courseDto.setName("Test Course");

        when(courseService.getCourse(courseId)).thenReturn(CourseMapper.toCourse(courseDto));

        mockMvc.perform(get("/api/course/{id}", courseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid").value(courseId.toString()))
                .andExpect(jsonPath("$.name").value("Test Course"));
    }

    @Test
    public void testCreateCourse() throws Exception {
        UserDto teacherDto = new UserDto();
        teacherDto.setUuid(UUID.randomUUID());
        teacherDto.setName("Teacher Name");

        User teacher = UserMapper.toUser(teacherDto);

        Course course = new Course();
        course.setUuid(UUID.randomUUID());
        course.setName("New Course");
        course.setDescription("Description of course");
        course.setTeacher(teacher);

        when(courseService.createCourse(any(Course.class))).thenReturn(course);

        String jsonContent = "{ " +
                "\"name\":\"New Course\", " +
                "\"description\":\"Description of course\", " +
                "\"teacher\":{\"uuid\":\"" + teacher.getUuid() + "\", \"name\":\"" + teacher.getName() + "\"}" +
                "}";

        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateCourse() throws Exception {
        UUID courseId = UUID.randomUUID();

        CourseDto updatedCourse = new CourseDto();
        updatedCourse.setUuid(courseId);
        updatedCourse.setName("Updated Course");
        updatedCourse.setDescription("Updated course description");
        updatedCourse.setTeacher(new UserDto());

        when(courseService.updateCourse(eq(courseId), any(Course.class))).thenReturn(CourseMapper.toCourse(updatedCourse));

        mockMvc.perform(put("/api/course/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Course\", \"description\":\"Updated course description\", \"teacher\":{\"uuid\":\"" + updatedCourse.getTeacher().getUuid() + "\"}}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCourse() throws Exception {
        UUID courseId = UUID.randomUUID();

        mockMvc.perform(delete("/api/course/{id}", courseId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testEnrollUserToCourse() throws Exception {
        UUID courseId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        mockMvc.perform(post("/api/course/{courseId}/enroll/{userId}", courseId, userId))
                .andExpect(status().isOk());
    }
}
