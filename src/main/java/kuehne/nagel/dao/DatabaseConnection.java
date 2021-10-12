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

    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER = "db.user";
    private static final String DATABASE_PASSWORD = "db.password";
    private static final Properties properties = new Properties();

    public static Connection getConnectionToDb() throws ApplicationPropertiesException, CannotReachDatabaseException {
        Connection attemptToConnect;
        try {
            attemptToConnect = DriverManager.getConnection(
                    return_property(DATABASE_URL),
                    return_property(DATABASE_USER),
                    return_property(DATABASE_PASSWORD));
        } catch (IOException ioex) {
            throw new ApplicationPropertiesException();
        } catch (SQLException sqlex) {
            throw new CannotReachDatabaseException();
        }
        System.out.println("Database is reachable! Can process SQL queries.");
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
