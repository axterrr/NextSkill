// TODO: create DTO for children of CourseObjectDTO class and use oneOf
package ukma.springboot.nextskill.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.dto.*;
import ukma.springboot.nextskill.exceptions.ErrorResponse;
import ukma.springboot.nextskill.services.CourseObjectService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courseObjects")
@Tag(name = "Course Objects", description = "Course Objects related API")
public class CourseObjectController {

    private final CourseObjectService courseObjectService;

    public CourseObjectController(CourseObjectService courseObjectService) {
        this.courseObjectService = courseObjectService;
    }

    @PostMapping("create")
    @Operation(summary = "Create a course object", description = "Creates a course object with provided data", tags = {"Course Objects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course object is created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Something in provided data is wrong", content = @Content(
                    mediaType = "application/json"))}
    )
    public ResponseEntity<CourseObjectDto> createCourseObject(
            @Parameter(description = "Data of a course object to be created")
            @Valid @RequestBody CourseObjectDto courseObjectDto) {
        CourseObjectDto createdObject = courseObjectService.createCourseObject(courseObjectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdObject);
    }

    @GetMapping("all")
    @Operation(summary = "Get all existing course objects", description = "Returns all existing course objects", tags = {"Course Objects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(
                            oneOf = {PostDto.class, AssignmentDto.class}
                    )))})}
    )
    public ResponseEntity<List<CourseObjectDto>> getAllCourseObjects() {
        List<CourseObjectDto> courseObjects = courseObjectService.getAllCourseObjects();
        return new ResponseEntity<>(courseObjects, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get information about a course object", description = "If exists, returns data about a course object with provided id", tags = {"Course Objects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course object is found", content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            oneOf = {PostDto.class, AssignmentDto.class}
                    ))}),
            @ApiResponse(responseCode = "404", description = "Course object is not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<CourseObjectDto> getCourseObject(
            @Parameter(description = "Id a course object")
            @PathVariable UUID uuid) {
        CourseObjectDto updatedCourseObject = courseObjectService.getCourseObject(uuid);
        return new ResponseEntity<>(updatedCourseObject, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Edit course object data", description = "If exists, updates information about a course object", tags = {"Course Objects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course object is updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Something in provided data is wrong", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course object is not found", content = @Content(
                    mediaType = "application/json"))}
    )
    public ResponseEntity<CourseObjectDto> updateCourseObject(
            @Parameter(description = "Id a course object to be updated")
            @PathVariable UUID uuid,
            @Parameter(description = "Data of a course object to be created")
            @Valid @RequestBody CourseObjectDto courseObjectDto) {
        CourseObjectDto updatedCourseObject = courseObjectService.updateCourseObject(uuid, courseObjectDto);
        return new ResponseEntity<>(updatedCourseObject, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a course object", description = "If exists, removes information about a course object", tags = {"Course Objects"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course object is deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Course object is not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<Void> deleteCourseObject(
            @Parameter(description = "Id a course object to be deleted")
            @PathVariable UUID uuid) {
        courseObjectService.deleteCourseObject(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
