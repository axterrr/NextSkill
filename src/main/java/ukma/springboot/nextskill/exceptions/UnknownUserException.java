package ukma.springboot.nextskill.exceptions;

public class UnknownUserException extends RuntimeException {
    public UnknownUserException(String username) {
        super("There is no user with '" + username + "' username present.");
    }
}
