package core.exceptions;


public class LoginException extends Exception {

    private ExType exType;

    public LoginException() { super(); }
    public LoginException(String message) { super(message); }
    public LoginException(String message, Throwable cause) { super(message, cause); }
    public LoginException(Throwable cause) { super(cause); }
    public LoginException(ExType exceptionType) {
        super(exceptionType.toString());
    }

    public enum ExType { INVALID_ID_PASS }

    public ExType getExType() {
        return ExType.valueOf(getMessage());
    }
}

