package ukma.springboot.nextskill.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ukma.springboot.nextskill.entities.UserEntity;
import ukma.springboot.nextskill.interfaces.IFileUploadService;
import ukma.springboot.nextskill.repositories.UserRepository;
import ukma.springboot.nextskill.services.LocalFileUploadService;
import ukma.springboot.nextskill.utils.CSVUtility;

@Configuration
@EnableScheduling
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            try {
                UserEntity userEntity = userRepository.findByUsername(username);
                if (userEntity == null) {
                    throw new UsernameNotFoundException("User not found: " + username);
                }
                return userEntity;
            } catch (Exception e) {
                throw new RuntimeException("An error occurred while retrieving user details.", e);
            }
        };
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CSVUtility csvUtility() {return new CSVUtility();}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    IFileUploadService fileUploadService() {
        return new LocalFileUploadService();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
