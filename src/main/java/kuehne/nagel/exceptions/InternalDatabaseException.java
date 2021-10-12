package kuehne.nagel.exceptions;

public class InternalDatabaseException extends Exception {
    public InternalDatabaseException() {
    }

    public InternalDatabaseException(String message) {
        super(message);
    }

    public InternalDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
    public void cannotProcessSqlQuery() {
        System.err.println("Cannot process SQL query...\nInvalid SQL syntax or table records doesn't exist");
    }
}
