package ukma.springboot.nextskill.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.dto.CourseObjectDto;
import ukma.springboot.nextskill.services.CourseObjectService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courseObjects")
public class CourseObjectController {

    private final CourseObjectService courseObjectService;

    public CourseObjectController(CourseObjectService courseObjectService) {
        this.courseObjectService = courseObjectService;
    }

    @PostMapping("/course-object")
    public ResponseEntity<CourseObjectDto> createCourseObject(@Valid @RequestBody CourseObjectDto courseObjectDto) {
        CourseObjectDto createdObject = courseObjectService.createCourseObject(courseObjectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdObject);
    }

    @GetMapping
    public ResponseEntity<List<CourseObjectDto>> getAllCourseObjects() {
        List<CourseObjectDto> courseObjects = courseObjectService.getAllCourseObjects();
        return new ResponseEntity<>(courseObjects, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CourseObjectDto> updateCourseObject(@PathVariable UUID uuid, @Valid @RequestBody CourseObjectDto courseObjectDto) {
        CourseObjectDto updatedCourseObject = courseObjectService.updateCourseObject(uuid, courseObjectDto);
        return new ResponseEntity<>(updatedCourseObject, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCourseObject(@PathVariable UUID uuid) {
        courseObjectService.deleteCourseObject(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
