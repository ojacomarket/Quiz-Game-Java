package kuehne.nagel.dao;

import kuehne.nagel.config.Config;
import kuehne.nagel.exceptions.ApplicationPropertiesException;
import kuehne.nagel.exceptions.CannotReachDatabaseException;
import kuehne.nagel.utils.DatabaseConnectivityProblems;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static kuehne.nagel.utils.DatabaseFailures.DATABASE_ABSENCE;
import static kuehne.nagel.utils.DatabaseFailures.TYPO_APPLICATION_PROPERTIES_FILE_NAME;

abstract public class DatabaseConnection {
    /**
     * Get connection to the database
     *
     * @param initialTransaction Type 0 if you connecting for the first time, while 1 will indicate other
     *                           transaction times within ONE operation (search, save, update, delete)
     * @return
     * @throws SQLException
     */
    public static Connection getConnectionToDb(int initialTransaction) throws ApplicationPropertiesException, CannotReachDatabaseException {
        if (initialTransaction > 1 || initialTransaction < 0) {
            return null;
        }
        Connection attemptToConnect = null;
        try {
            attemptToConnect = DriverManager.getConnection(
                    Config.return_property(Config.DATABASE_URL),
                    Config.return_property(Config.DATABASE_USER),
                    Config.return_property(Config.DATABASE_PASSWORD));
            if (initialTransaction == 0) {
                System.out.println("\t\t\t>>> Successfully connected to the database!\n");
            }
        } catch (IOException ioex) {
            System.out.println("Im herAAAAAe!");
            throw new ApplicationPropertiesException();

            // throw new IOException();
        } catch (SQLException sqlex) {
            System.out.println("Im GGG!");
            throw new CannotReachDatabaseException();
            //DatabaseConnectivityProblems.printConnectivityErrorMessage(DATABASE_ABSENCE);

           // throw new SQLException();
        }
        System.out.println("Im here!");
        return attemptToConnect;
    }
}
