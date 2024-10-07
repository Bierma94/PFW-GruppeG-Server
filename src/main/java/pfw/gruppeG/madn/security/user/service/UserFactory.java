package pfw.gruppeG.madn.security.user.service;



import org.springframework.stereotype.Service;
import pfw.gruppeG.madn.security.user.model.User;
import pfw.gruppeG.madn.security.user.model.UserRole;

import java.util.Date;

/**
 * Factory for creating User objects
 * @author Jannes Bierma
 * @created 29.09.2024
 */
@Service
public class UserFactory{

    /**
     * Creates a new user with the given parameters
     * @param username the username
     * @param password the password
     * @param role the role
     * @return the created user
     */
    public User createUser(String username, String password, String email, UserRole role)  {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .created(new Date())
                .build();

    }

    /**
     * Creates a new user with the given parameters and the role USER
     * @param username the username
     * @param password the password
     * @return the created user
     */
    public User createUser(String username, String password, String email) {
        return createUser(username, password, email, UserRole.USER);
    }

}
