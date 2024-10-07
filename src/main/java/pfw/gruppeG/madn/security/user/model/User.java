package pfw.gruppeG.madn.security.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * The User Entity
 * @author Jannes Bierma
 * @created 29.09.2024
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    /** The ID of the User */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    /** The Username of the User */
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    /** The Password of the User */
    @Column(name = "password", nullable = false)
    private String password;
    /** The Email of the User */
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    /** The Role of the User */
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    /** The Created Date of the User */
    @Column(name = "created", nullable = false)
    private Date created;

    /**
     * Returns the authorities of the user
     * @return the authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority role = () -> "ROLE_" + UserRole.USER.name();
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        roles.add(role);
        return roles;
    }
    /**
     * Returns the password of the user
     * @return String the password
     */
    @Override
    public String getPassword() {
        return password;
    }
    /**
     * Returns the username of the user
     * @return String the username
     */
    @Override
    public String getUsername() {
        return username;
    }
    /**
     * Returns if the account is not expired
     * @return boolean if the account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Returns if the account is not locked
     * @return boolean if the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
    /**
     * Returns if the credentials are not expired
     * @return boolean if the credentials are not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
    /**
     * Returns if the user is enabled
     * @return boolean if the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    /**
     * Returns the string representation of the user
     * @return the string representation
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", created=" + created +
                '}';
    }
}
