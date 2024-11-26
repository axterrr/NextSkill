package ukma.springboot.nextskill.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ukma.springboot.nextskill.security.JWTUtility;

import java.io.IOException;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTUtility jwtUtility;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //CredentialsView credentials = new ObjectMapper().readValue(request.getInputStream(), CredentialsView.class);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isBlank()) {
            throw new BadCredentialsException("Username cannot be empty.");
        }
        if (password == null || password.isBlank()) {
            throw new BadCredentialsException("Password cannot be empty.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtUtility.getToken(authResult.getName(), authResult.getAuthorities());
        // If authentication is successful we attach jwt token to a cookie (a better way to store it)
        response.addCookie(new Cookie("SECRET_TOKEN", token));
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //response.getWriter().write(failed.getMessage());
        //response.getWriter().flush();
        response.sendRedirect("/login?error");
    }
}
