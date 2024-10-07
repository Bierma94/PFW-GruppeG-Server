package pfw.gruppeG.madn.security.dto;

import lombok.*;

/**
 * Data transfer object for the login
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    /** String: username */
    private String username;
    /** String: password */
    private String password;
}
