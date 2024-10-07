package pfw.gruppeG.madn.security.dto;

import lombok.*;

import java.util.ArrayList;

/**
 * DTO for the LoginResponse
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResonse {
    /** String: token */
    private String token;
    /** ArrayList<String>: links */
    private ArrayList<String> links = new ArrayList<>();
}
