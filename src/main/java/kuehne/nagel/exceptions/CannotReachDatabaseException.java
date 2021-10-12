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
    public void cannotReachDatabaseError() {
        System.err.println("Cannot reach a database...\nCheck application.property file data correctness" +
                " or database existence!");
    }
}
