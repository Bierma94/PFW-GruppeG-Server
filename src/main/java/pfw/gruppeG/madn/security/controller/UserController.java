package pfw.gruppeG.madn.security.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pfw.gruppeG.madn.security.user.model.User;
import pfw.gruppeG.madn.security.user.service.UserService;

import java.util.Optional;

/**
 * Rest controller for the user
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /** The user service */
    private final UserService userService;

    /**
     * Autowired constructor for the user controller
     * @param userService
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get Userinformation
     * @return the user information
     */
    @GetMapping()
    public ResponseEntity<String> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findUserByUsername(authentication.getName());
        return user.map(value -> ResponseEntity.ok(value.toString())).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

}
