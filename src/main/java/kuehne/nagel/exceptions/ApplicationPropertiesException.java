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
    public void printApplicationPropertiesError() {
        System.err.println("Cannot reach a database...\nTypo in application properties file name!");
    }
}
