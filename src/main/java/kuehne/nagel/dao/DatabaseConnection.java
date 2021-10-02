package kuehne.nagel.dao;

import kuehne.nagel.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;

abstract public class DatabaseConnection {

    public static Connection getConnectionToDb() throws Exception {

        Connection connect_to_db = DriverManager.getConnection(
                Config.return_property(Config.DATABASE_URL),
                Config.return_property(Config.DATABASE_USER),
                Config.return_property(Config.DATABASE_PASSWORD)
        );
        System.out.println("\nSuccessfully connected to the database!\n");
        return connect_to_db;
    }
}
