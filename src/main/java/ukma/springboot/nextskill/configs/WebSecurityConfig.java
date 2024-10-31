package ukma.springboot.nextskill.configs;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ukma.springboot.nextskill.security.CustomAccessDeniedHandler;
import ukma.springboot.nextskill.security.CustomUnauthorizedHandler;
import ukma.springboot.nextskill.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    private CustomUnauthorizedHandler customUnauthorizedHandler;

    public WebSecurityConfig(
            JwtAuthFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider,
            CustomAccessDeniedHandler customAccessDeniedHandler,
            CustomUnauthorizedHandler customUnauthorizedHandler
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthenticationFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customUnauthorizedHandler = customUnauthorizedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                        .ignoringRequestMatchers("/**")
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handler ->
                        handler.authenticationEntryPoint(customUnauthorizedHandler)
                                .accessDeniedHandler(customAccessDeniedHandler)
                );

        return http.build();
    }

}
