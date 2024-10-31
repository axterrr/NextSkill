package ukma.springboot.nextskill.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.dto.CourseDto;
import ukma.springboot.nextskill.exceptions.ErrorResponse;
import ukma.springboot.nextskill.interfaces.ICourseService;
import ukma.springboot.nextskill.logging.markers.CompositeLogMarkers;
import ukma.springboot.nextskill.logging.markers.LogMarkers;
import ukma.springboot.nextskill.model.mappers.CourseMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/course")
@Tag(name = "Courses", description = "Courses related API")
public class CourseController {

    private static final Logger logger = LogManager.getLogger(CourseController.class);

    private final ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/all")
    @Operation(summary = "Get all existing courses", description = "Returns all courses available for current user", tags = {"Courses"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CourseDto.class)))})}
    )
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        logger.info(LogMarkers.COURSE_MARKER, "Fetching all courses for current user");
        List<CourseDto> courses = courseService.getAllCourses().stream().map(CourseMapper::toCourseDto).toList();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get information about a course by id", description = "If exists, returns data about a course with provided id", tags = {"Courses"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course is found", content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Course is not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<CourseDto> getCourse(
            @Parameter(description = "Id if a course")
            @PathVariable UUID id) {
        logger.info(LogMarkers.COURSE_MARKER, "Fetching course with id: {}", id);
        CourseDto course = CourseMapper.toCourseDto(courseService.getCourse(id));
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping(value = "create")
    @Operation(summary = "Create a course", description = "Creates a course with provided information", tags = {"Courses"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course is created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Something in provided data is wrong", content = @Content(
                    mediaType = "application/json"))}
    )
    @PreAuthorize("hasRole('TEACHER') || hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> addCourse(
            @Parameter(description = "Data of a course to be created")
            @Valid @RequestBody CourseDto course) {
        String sessionInfo = "session information available from controller";
        ThreadContext.put("sessionInfo", sessionInfo);
        logger.info(CompositeLogMarkers.COURSE_CREATE_MARKER, "Creating course with name: {}", course.getName());
        courseService.createCourse(CourseMapper.toCourse(course));
        ThreadContext.clearAll();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Edit a course", description = "If exists, updates information about a course", tags = {"Courses"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course is updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Something in provided data is wrong", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course is not found", content = @Content(
                    mediaType = "application/json"))}
    )
    @PreAuthorize("hasRole('TEACHER') || hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> updateCourse(
            @Parameter(description = "Id if a course")
            @PathVariable UUID id,
            @Parameter(description = "Updated data of a course")
            @Valid @RequestBody CourseDto course) {
        logger.info(LogMarkers.COURSE_MARKER, "Updating course with id: {}", id);

        courseService.updateCourse(id, CourseMapper.toCourse(course));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a course", description = "If exists, removes information about a course", tags = {"Courses"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course is deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Course is not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))}
    )
    @PreAuthorize("hasRole('TEACHER') || hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteCourse(
            @Parameter(description = "Id if a course")
            @PathVariable UUID id) {
        logger.info(LogMarkers.COURSE_MARKER, "Deleting course with id: {}", id);
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(value = "/{courseId}/enroll/{userId}")
    @Operation(summary = "Enroll a user to a course", description = "Enrolls a user to the specified course", tags = {"Courses"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully enrolled", content = @Content),
            @ApiResponse(responseCode = "404", description = "Course or User not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))}
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> enrollUserToCourse(
            @Parameter(description = "Id of the course")
            @PathVariable UUID courseId,
            @Parameter(description = "Id of the user")
            @PathVariable UUID userId) {
        logger.info(LogMarkers.COURSE_MARKER, "Enrolling user {} to course with id: {}", userId, courseId);

        courseService.enrollUserToCourse(courseId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
