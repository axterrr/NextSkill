package ukma.springboot.nextskill.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.dto.CourseDto;
import ukma.springboot.nextskill.interfaces.ICourseService;
import ukma.springboot.nextskill.model.mappers.CourseMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCourses().stream().map(CourseMapper::toCourseDto).toList();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable UUID id) {
        CourseDto course = CourseMapper.toCourseDto(courseService.getCourse(id));
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<HttpStatus> addCourse(@Valid @RequestBody CourseDto course) {
        courseService.createCourse(CourseMapper.toCourse(course));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> updateCourse(@PathVariable UUID id, @Valid @RequestBody CourseDto course) {
        courseService.updateCourse(id, CourseMapper.toCourse(course));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
