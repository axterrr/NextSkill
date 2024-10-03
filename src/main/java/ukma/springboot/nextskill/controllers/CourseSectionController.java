package ukma.springboot.nextskill.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.dto.CourseSectionDto;
import ukma.springboot.nextskill.interfaces.ICourseSectionService;
import ukma.springboot.nextskill.model.mappers.CourseSectionMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courseSection")
public class CourseSectionController {

    private final ICourseSectionService courseSectionService;

    @Autowired
    public CourseSectionController(ICourseSectionService courseSectionService) {
        this.courseSectionService = courseSectionService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<CourseSectionDto>> getAllCourseSections() {
        List<CourseSectionDto> courses = courseSectionService.getAllCourseSections().stream().map(CourseSectionMapper::toCourseSectionDto).toList();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseSectionDto> getCourseSection(@PathVariable UUID id) {
        CourseSectionDto course = CourseSectionMapper.toCourseSectionDto(courseSectionService.getCourseSection(id));
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<HttpStatus> addCourse(@Valid @RequestBody CourseSectionDto courseSection) {
        courseSectionService.createCourseSection(CourseSectionMapper.toCourseSection(courseSection));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> updateCourse(@PathVariable UUID id, @Valid @RequestBody CourseSectionDto courseSection) {
        courseSectionService.updateCourseSection(id, CourseSectionMapper.toCourseSection(courseSection));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable UUID id) {
        courseSectionService.deleteCourseSection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
