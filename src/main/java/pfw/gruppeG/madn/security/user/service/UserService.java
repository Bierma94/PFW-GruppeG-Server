package pfw.gruppeG.madn.security.user.service;


import pfw.gruppeG.madn.security.user.model.User;
import pfw.gruppeG.madn.security.user.model.UserRole;

import java.util.Optional;

/**
 * Interface for the UserService
 * @author Jannes Bierma
 * @created 29.09.2024
 */
public interface UserService {
    /** Finds a user by its username */
    Optional<User> findUserByUsername(String username);
    /** Saves a user to the database */
    void saveUser(User user);
    void saveUser(String username, String password, String email);
    void saveUser(String username, String password, UserRole role, String email);
    /** Creates a new user with User Role: USER */
    void deleteUser(User user);
    /** Updates a user */
    void updateUser(User user);

}
