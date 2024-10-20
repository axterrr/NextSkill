package ukma.springboot.nextskill.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ukma.springboot.nextskill.dto.LoginUserDto;
import ukma.springboot.nextskill.dto.RegisterUserDto;

@Service
public class AuthService {

    public ResponseEntity<?> authenticateUser(LoginUserDto loginUserDto) {
        // ???
        return ResponseEntity.ok("Login successful");
    }

    public ResponseEntity<?> registerUser(RegisterUserDto registerUserDto) {
        // ???
        return ResponseEntity.ok("User registered successfully");
    }
}
