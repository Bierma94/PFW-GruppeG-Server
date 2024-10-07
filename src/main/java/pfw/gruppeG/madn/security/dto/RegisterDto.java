package pfw.gruppeG.madn.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * Register DTO
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterDto {
    /** String: username */
    @NotNull @Length(min = 3, max = 30)
    private String username;
    /** String: email */
    @NotNull @Email
    private String email;
    /** String: password */
    @NotNull @Length(min = 8, max = 30)
    private String password;
}
