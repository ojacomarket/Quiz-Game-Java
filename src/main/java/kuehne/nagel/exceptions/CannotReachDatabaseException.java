package kuehne.nagel.exceptions;

public class CannotReachDatabaseException extends Exception{
    public CannotReachDatabaseException() {
    }

    public CannotReachDatabaseException(String message) {
        super(message);
    }

    public CannotReachDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
