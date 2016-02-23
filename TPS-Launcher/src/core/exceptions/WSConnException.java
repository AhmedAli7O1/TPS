package core.exceptions;

public class WSConnException extends Exception{
    public WSConnException() { super(); }
    public WSConnException(String message) { super(message); }
    public WSConnException(String message, Throwable cause) { super(message, cause); }
    public WSConnException(Throwable cause) { super(cause); }
}
