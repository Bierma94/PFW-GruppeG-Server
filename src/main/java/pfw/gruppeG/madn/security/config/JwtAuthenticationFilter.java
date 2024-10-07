package pfw.gruppeG.madn.security.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import pfw.gruppeG.madn.security.service.JsonWebTokenService;

import java.io.IOException;

/**
 * Filter for authenticating the user with the JWT
 * When receiving a request, this filter checks if the request contains a valid JWT
 * If the JWT is valid, the user is authenticated
 * If the Header does not contain a JWT, the request is passed to the next filter
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /** Handler exception resolver */
    private final HandlerExceptionResolver handlerExceptionResolver;
    /** The JWT Service */
    private final JsonWebTokenService jwtService;
    /** The UserDetailsService */
    private final UserDetailsService userDetailsService;

    /**
     * Autowired constructor
     * @param jwtService
     * @param userDetailsService
     * @param handlerExceptionResolver
     */
    public JwtAuthenticationFilter(
            JsonWebTokenService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }
    /**
     * Filters the request
     * @param request the request
     * @param response the response
     * @param filterChain the filter chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        // Return if the header is null or does not start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String username = jwtService.extractUsername(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (username != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}