package pfw.gruppeG.madn.security.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pfw.gruppeG.madn.security.dto.LoginDto;
import pfw.gruppeG.madn.security.dto.LoginResonse;
import pfw.gruppeG.madn.security.dto.RegisterDto;
import pfw.gruppeG.madn.security.dto.RegisterResponse;
import pfw.gruppeG.madn.security.service.AuthenticationService;

/**
 * Rest controller for the authentication
 * Registers a new user and login a user
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    /**
     * Registers a new user
     *
     * @param registerDto the register data transfer object
     * @return the response entity
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody @Valid RegisterDto registerDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RegisterResponse.builder()
                            .mesage(bindingResult.getFieldError().getDefaultMessage())
                            .build());
        }
        authenticationService.register(registerDto.getUsername(), registerDto.getEmail(), registerDto.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RegisterResponse.builder().mesage(registerDto.getUsername()).build());
    }
    /**
     * User login
     * @param loginDto the login data transfer object
     * @return the response entity with the token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            String token = authenticationService.authenticate(loginDto.getUsername(), loginDto.getPassword());
            return ResponseEntity.ok(LoginResonse.builder().token(token).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
