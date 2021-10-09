package kuehne.nagel.dao;

import kuehne.nagel.config.Config;
import kuehne.nagel.exceptions.ApplicationPropertiesException;
import kuehne.nagel.exceptions.CannotReachDatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class DatabaseConnection {
    /**
     * Get connection to the database
     * @param initialTransaction 0 if connection for the first time within SQL QUERY, 1 if not for the first time
     * @return null, if (initalTransaction < 0 || > 1), otherwise connection to the database returned
     * @throws ApplicationPropertiesException if error in application.properties file
     * @throws CannotReachDatabaseException can caused by many factors, most common are: database is down or database privileges
     */
    public static Connection getConnectionToDb(int initialTransaction) throws ApplicationPropertiesException, CannotReachDatabaseException {
        if (initialTransaction > 1 || initialTransaction < 0) {
            return null;
        }
        Connection attemptToConnect;
        try {
            attemptToConnect = DriverManager.getConnection(
                    Config.return_property(Config.DATABASE_URL),
                    Config.return_property(Config.DATABASE_USER),
                    Config.return_property(Config.DATABASE_PASSWORD));
            if (initialTransaction == 0) {
                System.out.println("\t\t\t>>> Successfully connected to the database!\n");
            }
        } catch (IOException ioex) {
            throw new ApplicationPropertiesException();
        } catch (SQLException sqlex) {
            throw new CannotReachDatabaseException();
        }
        return attemptToConnect;
    }
}
