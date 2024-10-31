package ukma.springboot.nextskill.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import ukma.springboot.nextskill.security.filter.ExceptionHandlerFilter;
import ukma.springboot.nextskill.security.filter.AuthenticationFilter;
import ukma.springboot.nextskill.security.filter.JWTAuthorizationFilter;

@Configuration
public class SecurityConfig {

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${TOKEN_EXPIRATION}")
    private long jwtExpiration;

    private final AuthenticationManager authenticationManager;

    public SecurityConfig(AuthenticationManager manager) {
        this.authenticationManager = manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/user/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/course/create").hasRole("TEACHER")
                .requestMatchers(HttpMethod.PUT, "/api/course/*").hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/course/*").hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/course/*/enroll/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/courseSection/*").hasRole("TEACHER")
                .requestMatchers(HttpMethod.PUT, "/api/courseSection/*").hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/courseSection/*").hasAnyRole("TEACHER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/courseObjects/*").hasRole("TEACHER")
                .requestMatchers(HttpMethod.PUT, "/api/courseObjects/*").hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/courseObjects/*").hasAnyRole("TEACHER", "ADMIN")

                .anyRequest().authenticated())
            .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(new AuthenticationFilter(authenticationManager, secretKey, jwtExpiration))
            .addFilterAfter(new JWTAuthorizationFilter(secretKey), AuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
}
