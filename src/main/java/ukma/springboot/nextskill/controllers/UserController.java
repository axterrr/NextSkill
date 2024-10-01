package ukma.springboot.nextskill.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ukma.springboot.nextskill.dto.UserDto;
import ukma.springboot.nextskill.interfaces.IUserService;
import ukma.springboot.nextskill.model.mappers.UserMapper;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers().stream().map(UserMapper::toUserDto).toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        UserDto user = UserMapper.toUserDto(userService.getUser(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<HttpStatus> addUser(@Valid @RequestBody UserDto user) {
        userService.createUser(UserMapper.toUser(user));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable String id, @Valid @RequestBody UserDto user) {
        userService.updateUser(id, UserMapper.toUser(user));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
