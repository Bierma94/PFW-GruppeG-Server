package pfw.gruppeG.madn.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pfw.gruppeG.madn.security.user.service.UserService;

/**
 * Configuration for the security
 * @author  Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@Configuration
@Slf4j
public class Config {
    /** The user service */
    private final UserService userService;

    /**
     * Autowired constructor
     * @param userService
     */
    public Config(UserService userService) {
        this.userService = userService;
    }
    /**
     * UserDetailsService Bean
     * @return the userDetailsService
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userService.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    /**
     * PasswordEncoder Bean
     * @return BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * AuthenticationManager Bean
     * @param config  AuthenticationConfiguration
     * @return the authenticationManager
     * @throws Exception
     */
    @Bean
     AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    /**
     * AuthenticationProvider Bean
     * @return the authenticationProvider
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
