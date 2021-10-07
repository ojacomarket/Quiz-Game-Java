package kuehne.nagel.exceptions;

public class ApplicationPropertiesException extends Exception{
    public ApplicationPropertiesException() {
    }

    public ApplicationPropertiesException(String message) {
        super(message);
    }

    public ApplicationPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
