package ukma.springboot.nextskill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ukma.springboot.nextskill.dto.LoginUserDto;
import ukma.springboot.nextskill.dto.RegisterUserDto;
import ukma.springboot.nextskill.entities.RoleEntity;
import ukma.springboot.nextskill.model.Role;
import ukma.springboot.nextskill.model.User;
import ukma.springboot.nextskill.model.mappers.RoleMapper;
import ukma.springboot.nextskill.model.mappers.UserMapper;
import ukma.springboot.nextskill.repositories.RoleRepository;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.responses.LoginResponse;
import ukma.springboot.nextskill.security.JwtService;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public ResponseEntity<LoginResponse> authenticateUser(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getUsername(),
                        loginUserDto.getPassword()
                )
        );

        User authenticatedUser = UserMapper.toUser(userRepository.findByUsername(loginUserDto.getUsername()));

        String jwtToken = jwtService.generateToken(UserMapper.toUserEntity(authenticatedUser));

        LoginResponse loginResponse = new LoginResponse().setToken("Bearer " + jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    public ResponseEntity<?> registerUser(RegisterUserDto registerUserDto) {
        if (userRepository.existsByUsername(registerUserDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setName(registerUserDto.getName());
        user.setSurname(registerUserDto.getSurname());

        Optional<RoleEntity> opt = roleRepository.findById(1);
        if (opt.isPresent()) {
            HashSet<Role> roles = new HashSet<>();
            roles.add(RoleMapper.toRole(opt.get()));
            user.setRoles(roles);
        }
        user.setPasswordHash(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setEmail(registerUserDto.getEmail());

        userRepository.save(UserMapper.toUserEntity(user));
        return ResponseEntity.ok("User registered successfully");
    }
}
