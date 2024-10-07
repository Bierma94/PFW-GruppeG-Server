package pfw.gruppeG.madn.security.user.exception;

/**
 * Exception for User related errors
 * @author Jannes Bierma
 * @created 29.09.2024
 */
public class UserException extends Exception{

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}
