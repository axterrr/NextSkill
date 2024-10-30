package ukma.springboot.nextskill.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ukma.springboot.nextskill.dto.CourseDto;
import ukma.springboot.nextskill.interfaces.ICourseService;
import ukma.springboot.nextskill.model.mappers.CourseMapper;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICourseService courseService;

    // Тест для методу getAllCourses()
    @Test
    public void testGetAllCourses() throws Exception {
        when(courseService.getAllCourses()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/course/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // Тест для методу getCourse()
    @Test
    public void testGetCourseById() throws Exception {
        UUID courseId = UUID.randomUUID();
        CourseDto courseDto = new CourseDto();
        when(courseService.getCourse(courseId)).thenReturn(CourseMapper.toCourse(courseDto));

        mockMvc.perform(get("/api/course/{id}", courseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.name").value("Test Course"));
    }

    // Тест для методу addCourse()
    @Test
    public void testCreateCourse() throws Exception {
        CourseDto newCourse = new CourseDto();
        when(courseService.createCourse(any())).thenReturn(CourseMapper.toCourse(newCourse));

        mockMvc.perform(post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Course\"}"))
                .andExpect(status().isCreated());
    }

    // Тест для методу updateCourse()
    @Test
    public void testUpdateCourse() throws Exception {
        UUID courseId = UUID.randomUUID();
        CourseDto updatedCourse = new CourseDto();

        mockMvc.perform(put("/api/course/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Course\"}"))
                .andExpect(status().isNoContent());
    }

    // Тест для методу deleteCourse()
    @Test
    public void testDeleteCourse() throws Exception {
        UUID courseId = UUID.randomUUID();

        mockMvc.perform(delete("/api/course/{id}", courseId))
                .andExpect(status().isNoContent());
    }

    // Тест для методу enrollUserToCourse()
    @Test
    public void testEnrollUserToCourse() throws Exception {
        UUID courseId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        mockMvc.perform(post("/api/course/{courseId}/enroll/{userId}", courseId, userId))
                .andExpect(status().isOk());
    }
}
