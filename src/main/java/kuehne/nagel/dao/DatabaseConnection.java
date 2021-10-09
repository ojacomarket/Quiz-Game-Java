package kuehne.nagel.dao;

import kuehne.nagel.exceptions.ApplicationPropertiesException;
import kuehne.nagel.exceptions.CannotReachDatabaseException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

abstract public class DatabaseConnection {

    public static final String DATABASE_URL = "db.url";
    public static final String DATABASE_USER = "db.user";
    public static final String DATABASE_PASSWORD = "db.password";
    private static final Properties properties = new Properties();

    /**
     * Get connection to the database
     *
     * @param initialTransaction 0 if connection for the first time within SQL QUERY, 1 if not for the first time
     * @return null, if (initalTransaction < 0 || > 1), otherwise connection to the database returned
     * @throws ApplicationPropertiesException if error in application.properties file
     * @throws CannotReachDatabaseException   can caused by many factors, most common are: database is down or database privileges
     */
    public static Connection getConnectionToDb(int initialTransaction) throws ApplicationPropertiesException, CannotReachDatabaseException {
        if (initialTransaction > 1 || initialTransaction < 0) {
            return null;
        }
        Connection attemptToConnect;
        try {
            attemptToConnect = DriverManager.getConnection(
                    return_property(DATABASE_URL),
                    return_property(DATABASE_USER),
                    return_property(DATABASE_PASSWORD));
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

    //Read application.properties file, and store its data inside Properties object, which then
    //is queried by DriverManager class
    private synchronized static String return_property(String property_name) throws IOException {
        if (properties.isEmpty()) {
            try (
                    BufferedInputStream inputStream = new BufferedInputStream(
                            new FileInputStream("src/main/resources/application.properties"))
            ) {
                properties.load(inputStream);
            }
        }
        return properties.getProperty(property_name);
    }
}
