package pfw.gruppeG.madn.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * The Security Configuration
 * Filters the requests and sets authorization
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {
    /** The Authentication Provider */
    private final AuthenticationProvider authenticationProvider;
    /** The JwtAuthenticationFilter */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    /** The UserDetailsService */
    private final UserDetailsService userDetailsService;

    /**
     * Autowired Constructor for the SecurityConfiguration
     * @param authenticationProvider
     * @param jwtAuthenticationFilter
     * @param userDetailsService
     */
    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter, UserDetailsService userDetailsService) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
    }
    /**
     * Configures the security filter chain
     * @param http HttpSecurity object to configure
     * @return the security filter chain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((auth) -> auth
                            .requestMatchers("/auth/**").permitAll()
                            .requestMatchers("/user/**").hasRole("USER")
                            .requestMatchers("/h2-console/**").permitAll()
                            .anyRequest().authenticated())
            .sessionManagement((session) ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(authenticationProvider)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    /**
     * Configures the Cors Configuration
     * @return the Cors Configuration Source
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("**"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}