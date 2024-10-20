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
import ukma.springboot.nextskill.dto.UserDto;
import ukma.springboot.nextskill.exceptions.ErrorResponse;
import ukma.springboot.nextskill.interfaces.IUserService;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.model.mappers.UserMapper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Users", description = "Users related API")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all")
    @Operation(summary = "Get all existing users", description = "Returns all existing users", tags = {"Users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))})}
    )
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers().stream().map(UserMapper::toUserDto).toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    @Operation(summary = "Get information about a user by id", description = "If exists, returns data about a user with provided id", tags = {"Users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is found", content = { @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "User is not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<UserDto> getUser(
            @Parameter(description = "Id if a user")
            @PathVariable UUID id) {
        UserDto user = UserMapper.toUserDto(userService.getUser(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "create")
    @Operation(summary = "Create a user", description = "Creates a user with provided data", tags = {"Users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Something in provided data is wrong", content = @Content(
                    mediaType = "application/json"))}
    )
    public ResponseEntity<UserDto> addUser(
            @Parameter(description = "Data of a user to be created")
            @Valid @RequestBody UserDto user) {
        User createdUser = userService.createUser(UserMapper.toUser(user));
        UserDto toReturn = UserMapper.toUserDto(createdUser);
        return new ResponseEntity<>(toReturn, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Edit user data", description = "If exists, updates information about a user", tags = {"Users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Something in provided data is wrong", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User is not found", content = @Content(
                    mediaType = "application/json"))}
    )
    public ResponseEntity<HttpStatus> updateUser(
            @Parameter(description = "Data of a user to be updated")
            @PathVariable UUID id, @Valid @RequestBody UserDto user) {
        userService.updateUser(id, UserMapper.toUser(user));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a user", description = "If exists, removes information about a user", tags = {"Users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))}
    )
    public ResponseEntity<HttpStatus> deleteUser(
            @Parameter(description = "Id if a user")
            @PathVariable UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
