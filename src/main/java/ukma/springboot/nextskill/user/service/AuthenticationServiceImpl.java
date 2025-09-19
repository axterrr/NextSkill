package ukma.springboot.nextskill.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ukma.springboot.nextskill.common.exceptions.ResourceNotFoundException;
import ukma.springboot.nextskill.common.models.entities.UserEntity;
import ukma.springboot.nextskill.user.UserService;

import java.util.List;

@Component
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationManager {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserEntity user;
        try {
            user = userService.getUserByUsername(authentication.getPrincipal().toString());
        } catch (ResourceNotFoundException e) {
            throw new BadCredentialsException("Incorrect Username");
        }
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPasswordHash())) {
            throw new BadCredentialsException("Incorrect Password");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }
}
