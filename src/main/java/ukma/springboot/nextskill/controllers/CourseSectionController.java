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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.dto.CourseDto;
import ukma.springboot.nextskill.dto.CourseSectionDto;
import ukma.springboot.nextskill.exceptions.ErrorResponse;
import ukma.springboot.nextskill.interfaces.ICourseSectionService;
import ukma.springboot.nextskill.model.mappers.CourseSectionMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courseSection")
@Tag(name = "Course Sections", description = "Course Sections related API")
public class CourseSectionController {

    private final ICourseSectionService courseSectionService;

    @Autowired
    public CourseSectionController(ICourseSectionService courseSectionService) {
        this.courseSectionService = courseSectionService;
    }

    @GetMapping(value = "/all")
    @Operation(summary = "Get all sections of a course", description = "Returns all sections of a course", tags = {"Course Sections"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CourseSectionDto.class)))})}
    )
    public ResponseEntity<List<CourseSectionDto>> getAllCourseSections() {
        List<CourseSectionDto> courses = courseSectionService.getAllCourseSections().stream().map(CourseSectionMapper::toCourseSectionDto).toList();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get information about a course section by id",
            description = "If exists, returns data about a course section with provided id",
            tags = {"Course Sections"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course section is found", content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseSectionDto.class))}),
            @ApiResponse(responseCode = "404", description = "Course section is not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<CourseSectionDto> getCourseSection(
            @Parameter(description = "Id of a course section")
            @PathVariable UUID id) {
        CourseSectionDto course = CourseSectionMapper.toCourseSectionDto(courseSectionService.getCourseSection(id));
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping(value = "create")
    @Operation(summary = "Create a course section", description = "Creates a course section", tags = {"Course Sections"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course section is created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Something in provided data is wrong", content = @Content(
                    mediaType = "application/json"))}
    )
    public ResponseEntity<HttpStatus> addCourseSection(
            @Parameter(description = "Data of a course section")
            @Valid @RequestBody CourseSectionDto courseSection) {
        courseSectionService.createCourseSection(CourseSectionMapper.toCourseSection(courseSection));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Updates a course section", description = "Updates data of a course section", tags = {"Course Sections"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course section is updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Something in provided data is wrong", content = @Content(
                    mediaType = "application/json"))}
    )
    public ResponseEntity<HttpStatus> updateCourseSection(
            @Parameter(description = "Id of a course section")
            @PathVariable UUID id,
            @Parameter(description = "New data of a course section")
            @Valid @RequestBody CourseSectionDto courseSection) {
        courseSectionService.updateCourseSection(id, CourseSectionMapper.toCourseSection(courseSection));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a course section", description = "If exists, removes information about a course section", tags = {"Course Sections"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course section is deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Course section is not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<HttpStatus> deleteCourseSection(
            @Parameter(description = "Id of a course section")
            @PathVariable UUID id) {
        courseSectionService.deleteCourseSection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
