package at.yawk.buycraft.web;

/**
 * @author yawkat
 */
public class LoginException extends Exception {
    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class BadCredentialsException extends LoginException {
        public BadCredentialsException(String message) {
            super(message);
        }

        public BadCredentialsException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
