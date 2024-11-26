package ukma.springboot.nextskill.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ukma.springboot.nextskill.security.filters.AuthExceptionHandler;
import ukma.springboot.nextskill.security.filters.AuthenticationFilter;
import ukma.springboot.nextskill.security.filters.JWTAuthorizationFilter;

@Configuration
@EnableWebSecurity()
public class SecurityConfiguration {

    AuthExceptionHandler authExceptionHandler;
    JWTAuthorizationFilter jwtAuthorizationFilter;
    PasswordEncoder passwordEncoder;
    JWTUtility jwtUtility;
    AuthenticationManager authenticationManager;

    public SecurityConfiguration(AuthenticationManager manager, AuthExceptionHandler authExceptionHandler, JWTAuthorizationFilter jwtAuthorizationFilter, JWTUtility jwtUtility, PasswordEncoder passwordEncoder) {
        this.authenticationManager = manager;
        this.authExceptionHandler = authExceptionHandler;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")
                        .requestMatchers("error").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                )
                .addFilterBefore(authExceptionHandler, AuthenticationFilter.class)
                .addFilter(new AuthenticationFilter(authenticationManager, jwtUtility))
                .addFilterAfter(jwtAuthorizationFilter, AuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }
}