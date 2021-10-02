package kuehne.nagel.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public static final String DATABASE_URL = "db.url";
    public static final String DATABASE_USER = "db.user";
    public static final String DATABASE_PASSWORD = "db.password";

    //Name-Value list (almost like Map)
    private static Properties properties = new Properties();

    //threads handle
    public synchronized static String return_property (String property_name) throws IOException {
        if (properties.isEmpty()) {
           try (
                   BufferedInputStream inputStream = new BufferedInputStream(
                           //from FileInputStream perspective, our file is located at "nagel" project folder
                           //to start moving it through hierarchy use reference directories
                           new FileInputStream("src/main/resources/application.properties"))
           ) {
               properties.load(inputStream);
           } catch (IOException ioex) {
               //ioex.printStackTrace();
               //if properties failed to load APP GOES DOWN
               System.err.println("\nError occurred inside Config class --> return_property method\n");
               throw new IOException();
           }
        }
        return properties.getProperty(property_name);
    }
}
