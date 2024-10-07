package pfw.gruppeG.madn.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pfw.gruppeG.madn.security.user.service.UserService;

/**
 * Service for authenticating users
 * Registers and authenticates users
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@Service
@Slf4j
public class AuthenticationService {

    /** The UserService */
    private final UserService userService;
    /** The JsonWebTokenService */
    private final JsonWebTokenService jwtService;
    /** The AuthenticationManager */
    private final AuthenticationManager authenticationManager;
    /** The PasswordEncoder */
    private final PasswordEncoder passwordEncoder;

    /**
     * Autowired constructor for the AuthenticationService
     * @param userService
     * @param jwtService
     * @param authenticationManager
     * @param passwordEncoder
     */
    public AuthenticationService(UserService userService, JsonWebTokenService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user
     * @param username username of the user
     * @param email email of the user
     * @param password password of the user
     */
    public void register(String username,String email, String password) {

        userService.saveUser(username,
                passwordEncoder.encode(password),
                email);
    }

    /**
     * Authenticates a user
     * @param username username of the user
     * @param password password of the user
     * @return String JsonWebToken
     */
    public String authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtService.generateToken(userService.findUserByUsername(username).get());
    }

}
