package kuehne.nagel.dao;

import kuehne.nagel.config.Config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class DatabaseConnection {

    public static Connection getConnectionToDb() throws SQLException{
        Connection connect_to_db;
        try {
            connect_to_db = DriverManager.getConnection(
                    Config.return_property(Config.DATABASE_URL),
                    Config.return_property(Config.DATABASE_USER),
                    Config.return_property(Config.DATABASE_PASSWORD)
            );
        } catch (IOException ioex) {
            //System.err.println("\nError occurred inside Config class --> return_property method\n");
            return null;
        }
        catch (SQLException sqlex) {
            System.err.println("\nError occurred inside DatabaseConnection class --> getConnectionToDb method\n");
            throw new SQLException();
        }
        System.out.println("\nSuccessfully connected to the database!\n");
        return connect_to_db;
    }
}
