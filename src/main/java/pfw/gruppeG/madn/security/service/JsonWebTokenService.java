package pfw.gruppeG.madn.security.service;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

/**
 * The JsonWebTokenService interface
 * @author Jannes Bierma
 * @version 1.0 - 30.09.2024
 */
public interface JsonWebTokenService {
    /** Extracts the username from the token */
    String extractUsername(String token);
    /** Extracts Claim */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    /** Generates a token for the given user */
    String generateToken(UserDetails userDetails);
    /** Extracts the expiration date from the token */
    long getExpiration(String token);
    /** Validates the token */
    boolean isTokenValid(String token, UserDetails userDetails);
    /** Checks if the token is expired */
    boolean isTokenExpired(String token);

}
