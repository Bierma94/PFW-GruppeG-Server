package pfw.gruppeG.madn.security.user.service;


import org.springframework.stereotype.Service;
import pfw.gruppeG.madn.security.user.model.User;
import pfw.gruppeG.madn.security.user.model.UserRole;
import pfw.gruppeG.madn.security.user.repository.UserRepository;

import java.util.Optional;

/**
 * Implementation of the UserService interface
 * @author Jannes Bierma
 * @created 29.09.2024
 */
@Service
public class UserServiceImpl implements UserService{

    /** The UserRepository */
    private final UserRepository userRepository;
    /** The UserFactory */
    private final UserFactory userFactory;

    /**
     * Constructor for the UserServiceImpl
     * @param userRepository
     * @param userFactory
     */
    public UserServiceImpl(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    /**
     * Finds a user by its username
     * @param username
     * @return
     */
    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Saves a user to the database
     * @param user
     */
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Creates a new user with User Role: USER
     * @param username
     * @param password
     */
    @Override
    public void saveUser(String username, String password, String email) {
        User user = userFactory.createUser(username, password, email);
        userRepository.save(user);
    }

    /**
     * Creates a new user with the specified role
     * @param username
     * @param password
     * @param role
     */
    @Override
    public void saveUser(String username, String password, UserRole role, String email){
        User user = userFactory.createUser(username, password, email, role);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes a user from the database
     * @param user
     */
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    /**
     * Updates a user in the database
     * @param user
     */
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
