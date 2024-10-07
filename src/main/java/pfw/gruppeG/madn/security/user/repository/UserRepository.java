package pfw.gruppeG.madn.security.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pfw.gruppeG.madn.security.user.model.User;

import java.util.Optional;
/**
 * Repository for the User
 * @author Jannes Bierma
 * @created 29.09.2024
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /** Finds a user by its username */
    Optional<User> findByUsername(String username);

}